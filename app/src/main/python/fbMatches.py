import http.client
import json
from pprint import pprint
from datetime import datetime, date
# from pytz import timezone

def getFBMatches():

    conn = http.client.HTTPSConnection("api.sofascore.com")

    payload = ""

    headers = {
        'authority': "api.sofascore.com",
        'pragma': "no-cache",
        'cache-control': "no-cache",
        'sec-ch-ua': "^\^Chromium^^;v=^\^94^^, ^\^Google"
        }

    conn.request("GET", "/api/v1/category/352/scheduled-events/" + str(date.today()) , payload, headers)

    res = conn.getresponse()
    data = res.read()

    jsonData = (data.decode("utf-8"))
    jsonDict = json.loads(data)
    ret = dict()
    i = "1"
    for e in jsonDict["events"]:
        curdate = date.today()
        timestamp = int(e['startTimestamp'])
        dt_object = datetime.fromtimestamp(timestamp)
        evedate = dt_object.date()
        if evedate != curdate:
            continue
        # if dt_object.time() > datetime.today().time():
        #     x = 'Not started'
        # else:
        #     x = 'Started'
        win = 'Winner: '
        if e['status']['description'] == 'Ended':
            if e['homeScore']['current'] > e['awayScore']['current']:
                win += e['homeTeam']['name']
            elif e['homeScore']['current'] < e['awayScore']['current']:
                win += e['awayTeam']['name']
            else:
                win += 'Match tied'
        else:
            win += '-----'
        ret[i] = [e['tournament']['name'], e['homeTeam']['name'], e['awayTeam']['name'], ' ' if not bool(e['homeScore']) else e['homeScore']['current'], ' ' if not bool(e['awayScore']) else e['awayScore']['current'], str(dt_object.time())[:5] + ' - ' + e['status']['description'] + (' (FRO)' if e['finalResultOnly'] else ''), win]
        i = str(int(i) + 1)
    return ret;

pprint(getFBMatches())