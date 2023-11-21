from flask import Flask, request, jsonify
from flask_cors import CORS

import requests
import base64

app = Flask(__name__)
CORS(app)

@app.route('/decrypt', methods=['POST'])
def decryption_service():
    if not request.json:
        return jsonify({'error': 'No data'}), 400
    
    data = ''
    # if request.json['RawPayload'] and not request.json['Payload']:
    #     data = base64.b64decode(request.json['RawPayload'])
    #     data = data.decode('latin-1')

    # url = 'http://water-temperature-service-alb-1534773001.us-east-2.elb.amazonaws.com/waterTemp/add'
    # r = requests.post(url, json=jsonify(data))

    url = ''
    if request.json['RawPayload']['type'] == 'water-temperature':
        url = 'http://water-temperature-service-alb-1181570115.us-east-2.elb.amazonaws.com/waterTemp/add'
    elif request.json['RawPayload']['type'] == 'air-temperature':
        url = 'http://airtemp-service-alb-328567053.us-east-2.elb.amazonaws.com/airTemp/add'
    elif request.json['RawPayload']['type'] == 'humidity':
        url = 'http://humidity-service-alb-597444042.us-east-2.elb.amazonaws.com/humidity/add'

    r = requests.post(url, json=request.json)

    return str(r)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=6000)