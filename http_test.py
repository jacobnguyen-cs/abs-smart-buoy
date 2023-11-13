import requests
import datetime
import time
import json

headers = {
    "Authorization": "AWS4-HMAC-SHA256 Credential=AKIAS4K2HUFCYY7TIFWD/20231113/us-east-2/execute-api/aws4_request, SignedHeaders=content-length;content-type;host;x-amz-content-sha256;x-amz-date, Signature=3cebc30fa9b254717d86ab42abbfb4252dc1265fb8fd8c71a4def6507d904088",
    "X-Amz-Content-Sha256": "beaead3198f7da1e70d03ab969765e0821b24fc913697e929e726aeaebf0eba3",
    "X-Amz-Date": "20231113T041917Z",
    "Postman-Token": "<calculated when request is sent>",
    "Content-Type": "application/json",
    "Content-Length": "<calculated when request is sent>",
    "Host": "<calculated when request is sent>",
    "User-Agent": "PostmanRuntime/7.34.0",
    "Accept": "*/*",
    "Accept-Encoding": "gzip, deflate, br",
    "Connection": "keep-alive"
}


url = 'http://3.15.172.135:80/waterTemp/add'

#Request
time = str(round(time.time() * 1000))
data = {'temp': '666', 'time': time}



response = requests.post(url, json = data, headers = headers)

print('Status Code:', response.status_code)
print('Response Body:', response.text)

