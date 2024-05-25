from flask import Flask, request, jsonify
import jwt
import serial
import time
import requests

app = Flask(__name__)

backend_url = 'http://localhost:8080/api'
user_id = 0
truck_id = 0
parking_place_id = 0
token = ''
serial_port = ''
ser = None

class Truck:
    def __init__(self, height, length, width, weight):
        self.height = height
        self.length = length
        self.width = width
        self.weight = weight

    def to_dict(self):
        return {
            'height': self.height,
            'length': self.length,
            'width': self.width,
            'weight': self.weight
        }


class ParkingPlace:
    def __init__(self, minHeight, maxHeight, minLength, maxLength, minWidth, maxWidth, minWeight, maxWeight):
        self.minHeight = minHeight
        self.maxHeight = maxHeight
        self.minLength = minLength
        self.maxLength = maxLength
        self.minWidth = minWidth
        self.maxWidth = maxWidth
        self.minWeight = minWeight
        self.maxWeight = maxWeight

    def to_dict(self):
        return {
            'minHeight': self.minHeight,
            'maxHeight': self.maxHeight,
            'minLength': self.minLength,
            'maxLength': self.maxLength,
            'minWidth': self.minWidth,
            'maxWidth': self.maxWidth,
            'minWeight': self.minWeight,
            'maxWeight': self.maxWeight
        }


def send_truck_data_to_backend(truck):
    global token, user_id, truck_id
    headers = {
        'Authorization': f'Bearer {token}',
        'Content-Type': 'application/json'
    }
    payload = {
        'width': truck.width,
        'length': truck.length,
        'height': truck.height,
        'weight': truck.weight
    }
    response = requests.patch(backend_url + '/truck-manager/' + str(user_id) + '/trucks/' + str(truck_id), headers=headers, json=payload)
    return response


def send_parking_place_data_to_backend(parking_place):
    global token, user_id, parking_place_id
    headers = {
        'Authorization': f'Bearer {token}',
        'Content-Type': 'application/json'
    }
    payload = {
        'minWidth': parking_place.minWidth,
        'minLength': parking_place.minLength,
        'minHeight': parking_place.minHeight,
        'minWeight': parking_place.minWeight,
        'maxWidth': parking_place.maxWidth,
        'maxLength': parking_place.maxLength,
        'maxHeight': parking_place.maxHeight,
        'maxWeight': parking_place.maxWeight,
        'hourlyPay': 10.0
    }
    response = requests.patch(backend_url + '/parking-manager/' + str(user_id) + '/parking-places/' + str(parking_place_id), headers=headers, json=payload)
    return response


def get_user_id(token):
    global user_id
    try:
        decoded_token = jwt.decode(token, options={"verify_signature": False})
            
        user_id = decoded_token.get('id')
    
    except jwt.ExpiredSignatureError:
        print("Token has expired")
    except jwt.InvalidTokenError:
        print("Invalid token")


@app.route('/connect/truck', methods=['POST'])
def connect_arduino_truck():
    global token, truck_id, serial_port, ser
    token = request.headers.get('Authorization')
    if not token:
        return jsonify({"error": "Missing Authorization header"}), 400
    
    if token.startswith('Bearer '):
        token = token[7:]

    data = request.json
    serial_port = data.get('serial_port')
    truck_id = data.get('truck_id')
    get_user_id(token)

    if not serial_port:
        return jsonify({"error": "Missing serial_port"}), 400
    
    if not truck_id:
        return jsonify({"error": "Missing truck_id"}), 400

    try:
        if ser is None:
            ser = serial.Serial(serial_port, 9600)
        time.sleep(2) 
        ser.write(b'GET_DATA_TRUCK\n')
        response = ser.readline().decode('utf-8').strip()

        params = dict(item.split('=') for item in response.split('&'))

        truck = Truck(
            float(params.get('truck_height')),
            float(params.get('truck_length')),
            float(params.get('truck_width')),
            float(params.get('truck_weight'))
        )
        send_truck_data_to_backend(truck)
        return jsonify({"message": "Connected successfully"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    

@app.route('/connect/parking-place', methods=['POST'])
def connect_arduino_parking():
    global token, parking_place_id, serial_port, ser
    token = request.headers.get('Authorization')
    if not token:
        return jsonify({"error": "Missing Authorization header"}), 400
    
    if token.startswith('Bearer '):
        token = token[7:]

    data = request.json
    serial_port = data.get('serial_port')
    parking_place_id = data.get('parking_place_id')
    get_user_id(token)

    if not serial_port:
        return jsonify({"error": "Missing serial_port"}), 400
    
    if not parking_place_id:
        return jsonify({"error": "Missing parking_place_id"}), 400

    try:
        if ser is None:
            ser = serial.Serial(serial_port, 9600)
        time.sleep(2) 

        ser.write(b'GET_DATA_PARKING\n')
        response = ser.readline().decode('utf-8').strip()

        params = dict(item.split('=') for item in response.split('&'))

        parking_place = ParkingPlace(
            float(params.get('parking_minHeight')),
            float(params.get('parking_maxHeight')),
            float(params.get('parking_minLength')),
            float(params.get('parking_maxLength')),
            float(params.get('parking_minWidth')),
            float(params.get('parking_maxWidth')),
            float(params.get('parking_minWeight')),
            float(params.get('parking_maxWeight'))
        )

        send_parking_place_data_to_backend(parking_place)

        return jsonify({"message": "Connected successfully"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500


@app.route('/truck/get-data', methods=['GET'])
def get_truck_data_from_arduino():
    global token, serial_port, ser
    token = request.headers.get('Authorization')
    if not token:
        return jsonify({"error": "Missing Authorization header"}), 400
    
    if token.startswith('Bearer '):
        token = token[7:]

    if not serial_port:
        return jsonify({"error": "Missing serial_port"}), 400

    try:
        if ser is None:
            ser = serial.Serial(serial_port, 9600)
        time.sleep(2) 

        ser.write(b'GET_DATA_TRUCK\n')
        response = ser.readline().decode('utf-8').strip()

        params = dict(item.split('=') for item in response.split('&'))

        truck = Truck(
            float(params.get('truck_height')),
            float(params.get('truck_length')),
            float(params.get('truck_width')),
            float(params.get('truck_weight'))
        )

        return jsonify({
            "truck": truck.to_dict()
        }), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500
    

@app.route('/parking-place/get-data', methods=['GET'])
def get_data_from_arduino():
    global token, serial_port, ser
    token = request.headers.get('Authorization')
    if not token:
        return jsonify({"error": "Missing Authorization header"}), 400
    
    if token.startswith('Bearer '):
        token = token[7:]

    try:
        if ser is None:
            ser = serial.Serial(serial_port, 9600)
        time.sleep(2) 

        ser.write(b'GET_DATA_PARKING\n')
        response = ser.readline().decode('utf-8').strip()

        params = dict(item.split('=') for item in response.split('&'))

        parking_place = ParkingPlace(
            float(params.get('parking_minHeight')),
            float(params.get('parking_maxHeight')),
            float(params.get('parking_minLength')),
            float(params.get('parking_maxLength')),
            float(params.get('parking_minWidth')),
            float(params.get('parking_maxWidth')),
            float(params.get('parking_minWeight')),
            float(params.get('parking_maxWeight'))
        )

        return jsonify({
            "parking_place": parking_place.to_dict()
        }), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)