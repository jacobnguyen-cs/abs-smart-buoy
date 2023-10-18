import smbus
import time

# Get I2C bus
bus = smbus.SMBus(1)

while True:
    try:
        # SHT30 address, 0x44(68)
        # Send measurement command, 0x2C(44), 0x06(06) High repeatability measurement
        bus.write_i2c_block_data(0x44, 0x2C, [0x06])
    
        
        # Allow sensor to complete the measurement
        time.sleep(0.5)
        
        # Read data back, 6 bytes
        data = bus.read_i2c_block_data(0x44, 0xF4, 6)

        # Process and print the data
        freedom_temp = (((((data[0] * 256.0) + data[1]) * 175) / 65535.0) - 45) * 9/5 + 32
        humidity = 100 * (data[3] * 256 + data[4]) / 65535.0
        pressure = data[5]
        
        print("Pressure is:  %.2f %%hPa" % pressure)
        print("Relative Humidity : %.2f %%RH" % humidity)
        print("Air Temperature : %.2f F\n" % freedom_temp)
        

    except TimeoutError:
        print("Error: Connection timed out. Check the sensor and connections.")
    
    except Exception as e:
        print(f"Error: {e}")

    time.sleep(0.2)



