import paho.mqtt.client as mqtt
from datetime import datetime

topic = 'eec60104-a822-4fe3-89b5-4d6360b5f334'
broker_url = 'broker.hivemq.com'
broker_port = 1883


def on_connect(client, userdata, flags, rc):
    print(f'Connected with result code {rc}')
    client.subscribe(topic)


def on_message(client, userdata, msg):
    print(f'[{str(datetime.time(datetime.now()))}] - [{msg.topic}] - payload={msg.payload.decode("utf-8")}')


client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message
client.connect(broker_url, broker_port, 60)
client.loop_forever()
