import RPi.GPIO as gpio
import itertools

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

def swEvent(e):
    global current_color
    gpio.output(current_color, gpio.LOW)
    current_color = next(cycle)
    gpio.output(current_color, gpio.HIGH)

gpio.add_event_detect(swPin, gpio.FALLING, bouncetime=500, callback=swEvent)

def start():
    while True:
        pass
try:
    start()
except KeyboardInterrupt:
    print('Exit pressed Ctrl+C')
except Exception as e:
    print(e)
finally:
    print('End of program')
    gpio.cleanup()
    