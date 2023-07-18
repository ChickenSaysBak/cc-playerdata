# cc-playerdata
REST API for retrieving CozyCloud playerdata.

[![Documentation](https://img.shields.io/badge/Documentation-dark%20green?logo=swagger&logoColor=black
)]([https://discord.gg/vBEWAuY](https://cc-playerdata-webapp.azuremicroservices.io))
[![Discord](https://img.shields.io/discord/280048938000580609?logo=discord&logoColor=white&label=Discord&color=7289da)](https://discord.gg/vBEWAuY)

## Example usage

1. Retrieving data for the player named BurgerKing1:
<br>https://cc-playerdata-webapp.azuremicroservices.io/api/playerdata/username/BurgerKing1
```json
[
  {
    "uuid": "8f6723ca-c47c-4cd0-b979-14f528b4c82c",
    "username": "BurgerKing1",
    "firstPlayed": 1556583358213,
    "lastPlayed": 1679198428246,
    "rank": 5
  }
]
```
2. Retrieving all players that played when BurgerKing1 did (using the UUID from the previous response):
<br>https://cc-playerdata-webapp.azuremicroservices.io/api/playerdata/overlap/8f6723ca-c47c-4cd0-b979-14f528b4c82c
```
Data too large to display.
```
3. Limiting the previous request to just 3 players:
<br>https://cc-playerdata-webapp.azuremicroservices.io/api/playerdata/overlap/8f6723ca-c47c-4cd0-b979-14f528b4c82c?limit=3
```json
[
  {
    "uuid": "8f6723ca-c47c-4cd0-b979-14f528b4c82c",
    "username": "BurgerKing1",
    "firstPlayed": 1556583358213,
    "lastPlayed": 1679198428246,
    "rank": 5
  },
  {
    "uuid": "3d5eee55-8282-4466-9327-1862ff4b575b",
    "username": "JaxF2008",
    "firstPlayed": 1559093933705,
    "lastPlayed": 1679164242614,
    "rank": 0
  },
  {
    "uuid": "7d929e6e-676c-4548-9ab0-af51eef3f843",
    "username": "Insequent1443",
    "firstPlayed": 1560473253276,
    "lastPlayed": 1679165237974,
    "rank": 5
  }
]
```
#### More examples
* Retrieving all data:
<br>https://cc-playerdata-webapp.azuremicroservices.io/api/playerdata
* Retrieving the top 10 players with the longest time spans:
<br>https://cc-playerdata-webapp.azuremicroservices.io/api/playerdata/most_time?limit=10
* Retrieving players with usernames containing "diamond":
<br>https://cc-playerdata-webapp.azuremicroservices.io/api/playerdata/search/diamond
* Retrieving all players with elder rank:
<br>https://cc-playerdata-webapp.azuremicroservices.io/api/playerdata/rank/4
<br>OR
<br>https://cc-playerdata-webapp.azuremicroservices.io/api/playerdata/rank/elder
