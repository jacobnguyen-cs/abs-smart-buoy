from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend
from flask import Flask, request, jsonify
from flask_cors import CORS
from dotenv import load_dotenv

import pickle
import requests
import base64
import os

load_dotenv()
key = os.getenv('key')
nonce = os.getenv('nonce')

key = base64.b64decode(key.encode('utf-8'))
nonce = base64.b64decode(nonce.encode('utf-8'))

app = Flask(__name__)
CORS(app)

@app.route('/decrypt', methods=['POST'])
def decryption_service():
    if not request.json:
        return jsonify({'error': 'No data'}), 400

    ### Satellite Communication ###
    # if request.json['RawPayload'] and not request.json['Payload']:
    #     data = base64.b64decode(request.json['RawPayload'])
    #     data = data.decode('latin-1')

    cipher_text = base64.b64decode(request.json['cipher_text'])
    tag = base64.b64decode(request.json['tag'])
    data = decrypt_data(cipher_text, nonce, tag, key)
    data = pickle.loads(data)

    ### Satellite Communication ###
    # url = ''
    # if data['RawPayload']['type'] == 'water-temperature':
    #     url = 'http://water-temperature-service-alb-1181570115.us-east-2.elb.amazonaws.com/waterTemp/add'
    # elif data['RawPayload']['type'] == 'air-temperature':
    #     url = 'http://airtemp-service-alb-328567053.us-east-2.elb.amazonaws.com/airTemp/add'
    # elif data['RawPayload']['type'] == 'humidity':
    #     url = 'http://humidity-service-alb-597444042.us-east-2.elb.amazonaws.com/humidity/add'

    ### Over Internet ###
    url = 'http://console-service-alb-1397661684.us-east-2.elb.amazonaws.com/logs/add'
    if data['type'] == 'water-temperature':
        url = 'http://water-temperature-service-alb-1181570115.us-east-2.elb.amazonaws.com/waterTemp/add'
    elif data['type'] == 'air-temperature':
        url = 'http://airtemp-service-alb-328567053.us-east-2.elb.amazonaws.com/airTemp/add'
    elif data['type'] == 'humidity':
        url = 'http://humidity-service-alb-597444042.us-east-2.elb.amazonaws.com/humidity/add'
    elif data['type'] == 'adsb':
        url = 'http://adsb-alb-69257456.us-east-2.elb.amazonaws.com/adsbData/add'

    r = requests.post(url, json=data)
    return str(r)

def decrypt_data(cipher_text, nonce, tag, key):
    # Create an AES cipher object with the key and AES-GCM mode
    cipher = Cipher(algorithms.AES(key), modes.GCM(nonce, tag), backend=default_backend())
    decryptor = cipher.decryptor()

    # Decrypt the data
    decrypted_data = decryptor.update(cipher_text) + decryptor.finalize()

    return decrypted_data

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=6000)