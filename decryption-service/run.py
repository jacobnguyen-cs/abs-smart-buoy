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

    ### Satellite Communication ###
    # if request.json['RawPayload'] and not request.json['Payload']:
    #     data = base64.b64decode(request.json['RawPayload'])
    #     data = data.decode('latin-1')
    # url = ''
    # if data['RawPayload']['type'] == 'water-temperature':
    #     url = 'http://water-temperature-service-alb-1181570115.us-east-2.elb.amazonaws.com/waterTemp/add'
    # elif data['RawPayload']['type'] == 'air-temperature':
    #     url = 'http://airtemp-service-alb-328567053.us-east-2.elb.amazonaws.com/airTemp/add'
    # elif data['RawPayload']['type'] == 'humidity':
    #     url = 'http://humidity-service-alb-597444042.us-east-2.elb.amazonaws.com/humidity/add'

    url = ''
    if request.json['type'] == 'water-temperature':
        url = 'http://water-temperature-service-alb-1181570115.us-east-2.elb.amazonaws.com/waterTemp/add'
    elif request.json['type'] == 'air-temperature':
        url = 'http://airtemp-service-alb-328567053.us-east-2.elb.amazonaws.com/airTemp/add'
    elif request.json['type'] == 'humidity':
        url = 'http://humidity-service-alb-597444042.us-east-2.elb.amazonaws.com/humidity/add'

    r = requests.post(url, json=request.json)
    return str(r)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=6000)