from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend
from flask import Flask, request, jsonify
from dotenv import load_dotenv

import base64
import pickle
import os

load_dotenv()
key = os.getenv('key')
nonce = os.getenv('nonce')

key = base64.b64decode(key.encode('utf-8'))
nonce = base64.b64decode(nonce.encode('utf-8'))

app = Flask(__name__)

@app.route('/encrypt', methods=['POST'])
def encrypt_data():
    data = pickle.dumps(request.json)

    # Create an AES cipher object with the key and AES-GCM mode
    cipher = Cipher(algorithms.AES(key), modes.GCM(nonce), backend=default_backend())
    encryptor = cipher.encryptor()

    # Encrypt the data
    cipher_text = encryptor.update(data) + encryptor.finalize()

    # Get the authentication tag
    tag = encryptor.tag

    return jsonify({
       'cipher_text': base64.b64encode(cipher_text).decode(), 
       'tag': base64.b64encode(tag).decode()
    })

if __name__ == '__main__':
  app.run(host='0.0.0.0', port=7000)