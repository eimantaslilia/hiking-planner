# hiking-planner

Gives recommendations about a hiking trip based on the user's input.
 * Gear recommendations are based on the weather conditions - temperature, whether it's rainy, cloudy, clear sky, etc.
 * Food/water recommendations are mostly based on trip length
 * Weather data is retrieved using <a href="https://rapidapi.com/community/api/open-weather-map">Open Weather Map API</a>
  
<b>Note:</b> Api Key is required to get weather data, put it into application.properties.
  
Send a GET request with the following input:
```
localhost:8080/hiking-app/recommendations
```

```
{
    "tripLength": 25,
    "location": "vilnius"
}
```

Example output:

```
{
    "gearInfo": {
        "OPTIONAL": [
            "Trekking Poles",
            "A book",
            "Your dog"
        ],
        "ESSENTIALS": [
            "Hiking Backpack",
            "First Aid",
            "Knife",
            "Socks"
        ],
        "NIGHT": [
            "Sleeping Bag",
            "Tent",
            "Headlamp and Batteries",
            "Lighter",
            "Stove"
        ],
        "COLD": [
            "Jacket",
            "Vest",
            "Warm Hat",
            "Gloves/Mittens"
        ],
        "NAVIGATION": [
            "Compass",
            "GPS Device",
            "Satellite Messenger",
            "Map"
        ]
    },
    "weatherInfo": {
        "visibility": "10000",
        "city": "Vilnius",
        "temperature": "11.07",
        "description": "broken clouds",
        "humidity": "93",
        "wind_speed": "3.6",
        "main": "Clouds"
    },
    "nutrition": {
        "water": "6.0L",
        "estimated_calories": 4800,
        "foods": {
            "Trail Mix 100g": 600,
            "Dried Jerky": 250,
            "Apple": 150,
            "Chocolate 100g": 480,
            "Parmesan Cheese 100g": 321,
            "Smoked Bacon 100g": 533,
            "Instant Potatoes 100g": 364,
            "Dried Fruit 100g": 300
        }
    }
}
```
