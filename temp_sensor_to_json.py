import os
import glob
import time
import datetime
import json

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
        time.sleep(0.2)
        lines = read_temp_raw()
    equals_pos = lines[1].find('t=')
    if equals_pos != -1:
        temp_string = lines[1][equals_pos+2:]
        temp_c = float(temp_string) / 1000.0
        temp_f = temp_c * 9.0 / 5.0 + 32.0  # converting temperature to Fahrenheit
        return temp_f

data_list = []
		
while True:
    dtime = datetime.datetime.now()
    mydate = str(dtime.date())
    mytime = str(dtime.time())
    temperature = read_temp()

    # Creating a dictionary to hold date, time and temperature
    temp_data = {
        "date": mydate,
        "time": mytime,
        "temperature_F": temperature  # temperature in Fahrenheit
    }

    data_list.append(temp_data)

    # Converting dictionary to json format string
    json_data = json.dumps(temp_data, indent=4)

    # Printing json formatted data
    print(json_data)

    # Writing json formatted data to a file
    with open('log.json', 'w') as json_file:
        json.dump(data_list, json_file, indent=4)
    
    time.sleep(0.2)

    # You may want to add a condition to break the loop, otherwise it will run indefinitely
