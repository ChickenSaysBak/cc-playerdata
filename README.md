# cc-playerdata
REST API for retrieving CozyCloud playerdata.

[![Documentation](https://img.shields.io/badge/Documentation-dark%20green?logo=swagger&logoColor=black)](http://cc-playerdata-app.us-east-2.elasticbeanstalk.com)
[![Discord](https://img.shields.io/discord/280048938000580609?logo=discord&logoColor=white&label=Discord&color=7289da)](https://discord.gg/vBEWAuY)

## Example usage

1. Retrieving data for the player named BurgerKing1:
<br>http://cc-playerdata-app.us-east-2.elasticbeanstalk.com/api/playerdata/username/BurgerKing1
```json
[
  {
    "uuid": "8f6723ca-c47c-4cd0-b979-14f528b4c82c",
    "username": "BurgerKing1",
    "firstPlayed": 1556583358213,
    "lastPlayed": 1679198428246,
    "rank": 5,
    "owner": null
  }
]
```
2. Retrieving all players that played when BurgerKing1 did (using the UUID from the previous response):
<br>http://cc-playerdata-app.us-east-2.elasticbeanstalk.com/api/playerdata/overlap/8f6723ca-c47c-4cd0-b979-14f528b4c82c
```
Data too large to display.
```
3. Limiting the previous request to just 3 players:
<br>http://cc-playerdata-app.us-east-2.elasticbeanstalk.com/api/playerdata/overlap/8f6723ca-c47c-4cd0-b979-14f528b4c82c?limit=3
```json
[
  {
    "uuid": "8f6723ca-c47c-4cd0-b979-14f528b4c82c",
    "username": "BurgerKing1",
    "firstPlayed": 1556583358213,
    "lastPlayed": 1679198428246,
    "rank": 5,
    "owner": null
  },
  {
    "uuid": "3d5eee55-8282-4466-9327-1862ff4b575b",
    "username": "JaxF2008",
    "firstPlayed": 1559093933705,
    "lastPlayed": 1679164242614,
    "rank": 0,
    "owner": null
  },
  {
    "uuid": "7d929e6e-676c-4548-9ab0-af51eef3f843",
    "username": "Insequent1443",
    "firstPlayed": 1560473253276,
    "lastPlayed": 1679165237974,
    "rank": 5,
    "owner": null
  }
]
```
4. Finding all alt accounts for BurgerKing1 (using the UUID from example 1):
<br>http://cc-playerdata-app.us-east-2.elasticbeanstalk.com/api/playerdata/alts/8f6723ca-c47c-4cd0-b979-14f528b4c82c
```json
[
  {
    "uuid": "00000000-0000-0000-0009-01f0365451d6",
    "username": "~BalloonShark396",
    "firstPlayed": 1597705512806,
    "lastPlayed": 1637694119449,
    "rank": 0,
    "owner": "8f6723ca-c47c-4cd0-b979-14f528b4c82c"
  },
  {
    "uuid": "824a26cf-1fe8-42eb-b105-9fe33ba81120",
    "username": "KungFuPandaBro",
    "firstPlayed": 1620531828010,
    "lastPlayed": 1672365780570,
    "rank": 0,
    "owner": "8f6723ca-c47c-4cd0-b979-14f528b4c82c"
  }
]
```
#### More examples
* Retrieving all data:
<br>http://cc-playerdata-app.us-east-2.elasticbeanstalk.com/api/playerdata
* Retrieving the top 10 players with the longest time spans:
<br>http://cc-playerdata-app.us-east-2.elasticbeanstalk.com/api/playerdata/most_time?limit=10
* Retrieving players with usernames containing "diamond":
<br>http://cc-playerdata-app.us-east-2.elasticbeanstalk.com/api/playerdata/search/diamond
* Retrieving all players with elder rank:
<br>http://cc-playerdata-app.us-east-2.elasticbeanstalk.com/api/playerdata/rank/4
<br>OR
<br>http://cc-playerdata-app.us-east-2.elasticbeanstalk.com/api/playerdata/rank/elder

## HTTPS
If an HTTPS endpoint is required, you can use the following:
<br>https://fjbpf3sb3r4dpjauqwzebltr2y0gsqxa.lambda-url.us-east-2.on.aws

Simply add the desired path to the end of the url such as `/api/playerdata`
<br>See repository [here](https://github.com/ChickenSaysBak/cc-playerdata-to-https).
