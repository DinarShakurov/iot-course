import RPi.GPIO as gpio
import itertools
import paho.mqtt.client as mqtt

GREEN = 18
RED = 17
BLUE = 27
swPin = 4
cycle = itertools.cycle([GREEN, RED, BLUE])
current_color = next(cycle)

gpio.setmode(gpio.BCM)
gpio.setup(GREEN, gpio.OUT, initial=gpio.LOW)
gpio.setup(RED, gpio.OUT, initial=gpio.LOW)
gpio.setup(BLUE, gpio.OUT, initial=gpio.LOW)
gpio.setup(swPin, gpio.IN, pull_up_down=gpio.PUD_UP)

broker_url = 'broker.hivemq.com'
broker_port = 1883
topic = 'eec60104-a822-4fe3-89b5-4d6360b5f334'


def on_connect(cclient, userdata, flags, rc):
    print(f"Connected with result code {rc}")


client = mqtt.Client()
client.on_connect = on_connect
client.connect(broker_url, port=broker_port, keepalive=60)


def send(color, value):
    client.publish(topic, payload=f'{color}:{value}')


def get_color(color):
    if color == GREEN:
        return 'GREEN'
    elif color == RED:
        return 'RED'
    elif color == BLUE:
        return 'BLUE'
    else:
        return 'unknown'


def sw_event(e):
    global current_color
    gpio.output(current_color, gpio.LOW)
    send(get_color(current_color), gpio.LOW)
    current_color = next(cycle)
    gpio.output(current_color, gpio.HIGH)
    send(get_color(current_color), gpio.HIGH)


gpio.add_event_detect(swPin, gpio.FALLING, bouncetime=500, callback=sw_event)


def start():
    try:
        while True:
            pass
    except KeyboardInterrupt:
        print('Exit pressed Ctrl+C')
    except Exception as e:
        print(e)
    finally:
        print('End of program')
        gpio.cleanup()


start()
