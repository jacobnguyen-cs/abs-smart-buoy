from flask import Flask, request, jsonify
from flask_cors import CORS

import requests

app = Flask(__name__)
CORS(app)

@app.route('/decrypt', methods=['POST'])
def decryption_service():
    if not request.json:
        return jsonify({'error': 'No data'}), 400
    
    url = 'http://water-temperature-service-alb-1534773001.us-east-2.elb.amazonaws.com/waterTemp/add'
    r = requests.post(url, json=request.json)
    
    return str(r)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=6000)