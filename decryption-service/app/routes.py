from app import app
from flask import request, jsonify

@app.route('/decrypt', methods=['POST'])
def decryption_service():
    if not request.json:
        return jsonify({'error': 'No data'}), 400
    
    return request.json