import http.client
import json
from pprint import pprint
from datetime import datetime, date

def getHYMatches():
    conn = http.client.HTTPSConnection("www.hockeyindia.org")

    payload = ""

    headers = {
        'Connection': "keep-alive",
        'Pragma': "no-cache",
        'Cache-Control': "no-cache",
        'sec-ch-ua': "^\^Chromium^^;v=^\^94^^, ^\^Google"
        }

    conn.request("GET", "/sifeeds/repo/hockey/static/json/" + str(date.today().year) + "_tournament.json", payload, headers)

    res = conn.getresponse()
    data = res.read()

    # print(data.decode("utf-8"))
    jsonDict = json.loads(data)
    # print(jsonDict)
    ids = []

    # c = 0

    for s in jsonDict['seriesDetailsList']:
        if s['current_status'] == 'Ongoing': # change
            ids.append(s['id'])
            # c += 1
            # if c == 3:
            #     break

    # print(ids)
    ret = dict()
    i = "1"
    for _id in ids:
        conn = http.client.HTTPSConnection("www.hockeyindia.org")

        payload = ""

        headers = {
            'Connection': "keep-alive",
            'Pragma': "no-cache",
            'Cache-Control': "no-cache",
            'sec-ch-ua': "^\^Chromium^^;v=^\^94^^, ^\^Google"
            }

        conn.request("GET", "/sifeeds/repo/hockey/live/json/" + str(_id) + "_matches.json", payload, headers)

        res = conn.getresponse()
        data = res.read()
        jsonDict = json.loads(data)
        # print(jsonDict)
        series_name = jsonDict['seriesDetails']['seriesName']
        for e in jsonDict["allMatchesList"]:

            # if e['scheduleMatch'] == 'Today':
                # pprint(e)
                # curdate = date.today()
                # timestamp = int(e['startTimestamp'])
                # dt_object = datetime.fromtimestamp(timestamp)
                # evedate = dt_object.date()
                # if evedate != curdate:
                #     continue
                # if dt_object.time() > datetime.today().time():
                #     x = 'Not started'
                # else:
                #     x = 'Started'
            status = e['statusDisplay']
            if status == '':
                status = 'Not started'
            l1 = []
            for x in e['homeTeamGoalScorers']:
                l1.append(x['minutes'] + '\'' + x['fullName'])
            l2 = []
            for x in e['awayTeamGoalScorers']:
                l2.append(x['minutes'] + '\'' + x['fullName'])
            ret[i] = [series_name + ' (' + e['matchGroup'] + ')', e['homeTeamName'], e['awayTeamName'], e['homeTeamScore'], e['awayTeamScore'], e['gameTime'] + ' - ' + status, e['winning_team_name'], l1, l2]
            i = str(int(i) + 1)
            # if i == '5':
            #     break;
    return (ret);

# pprint(getHYMatches())