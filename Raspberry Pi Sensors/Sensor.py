import os
import glob
import time
import datetime
import json
import smbus

os.system('modprobe w1-gpio')
os.system('modprobe w1-therm')

base_dir = '/sys/bus/w1/devices/'
device_folder = glob.glob(base_dir + '28*')[0]
device_file = device_folder + '/w1_slave'

def read_temp_raw():
    with open(device_file, 'r') as f:
        lines = f.readlines()
    return lines

def read_temp():
    lines = read_temp_raw()
    while lines[0].strip()[-3:] != 'YES':
        time.sleep(1)
        lines = read_temp_raw()
    equals_pos = lines[1].find('t=')
    if equals_pos != -1:
        temp_string = lines[1][equals_pos+2:]
        temp_c = float(temp_string) / 1000.0
        temp_f = temp_c * 9.0 / 5.0 + 32.0  # converting temperature to Fahrenheit
        return temp_f

data_list = []

# Initialize I2C bus
bus = smbus.SMBus(1)

while True:
    try:
        dtime = datetime.datetime.now()
        mydate = str(dtime.date())
        mytime = str(dtime.time())
        #water_temperature = read_temp()
        water_temperature = "{:.3f}".format(read_temp()) 

        # Writing json formatted data to a file
        with open('log.json', 'w') as json_file:
            json.dump(data_list, json_file, indent=4)
        
        # SHT30 Measurement
        bus.write_i2c_block_data(0x44, 0x2C, [0x06])
        time.sleep(0.5)
        sht_data = bus.read_i2c_block_data(0x44, 0xF4, 6)

        # QMP6988 Measurement (make sure this part aligns with the sensor datasheet)
        qmp_data = bus.read_i2c_block_data(0x70, 0xF7, 8)  # Adjust the register and byte count as per datasheet

        # SHT30 Calculations
        air_temperature = "{:.3f}".format( ((((sht_data[0] * 256.0 + sht_data[1]) * 175 / 65535.0) - 45) * 9/5) + 32) 
        humidity = "{:.3f}".format(100*(sht_data[3] * 256 + sht_data[4]) / 65535.0)

        # QMP6988 Calculations (adjust this calculation as per the sensor datasheet)
        pressure_raw = (qmp_data[0] << 16) | (qmp_data[1] << 8) | qmp_data[2]
        pressure_hPa = (pressure_raw * 0.00625) / 100.0  # Adjust the conversion formula as per datasheet
        
        # Creating a dictionary for water temperature 
        water_temp_data = {
            "type": "water-temperature",
            "data": {
                "date": mydate,
                "time": mytime,
                "water-temperature": water_temperature  # temperature in Fahrenheit
            }
        }
     
        # Create a dictionary for air temperature 
        air_temp_data = {
            "type": "air-temperature",
            "data": {
                "date": mydate,
                "time": mytime,
                "air-temperature": air_temperature  # temperature in Fahrenheit
            }
        }
            
        # Create a dictioanry for Humidity data    
        humidity_data = {
            "type": "humidity",
            "data": {
                "date": mydate,
                "time": mytime,
                "humidity": humidity  # temperature in Fahrenheit
            }
        }
        
        
        #Append the data to the data list
        data_list.append(water_temp_data)
        data_list.append(air_temp_data)
        data_list.append(humidity_data)

        # Converting dictionary to json format string
        water_json_data = json.dumps(water_temp_data, indent=4)
        air_json_data = json.dumps(air_temp_data, indent = 4)
        humidity_json_data = json.dumps(humidity_data, indent = 4)

        # Printing json formatted data
        print(water_json_data)
        print(air_json_data)
        print(humidity_json_data)
            
    except TimeoutError:
        print("Connection timed out. Please check the sensors and connections.")

    except Exception as e:
        print(f"An error occurred: {e}")

    # Delay before the next reading
    time.sleep(1)

