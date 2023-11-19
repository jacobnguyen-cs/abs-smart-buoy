from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

@app.route('/decrypt', methods=['POST'])
def decryption_service():
    if not request.json:
        return jsonify({'error': 'No data'}), 400
    
    return request.json

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)