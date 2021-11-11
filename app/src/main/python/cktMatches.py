import http.client
import json
from pprint import pprint
from datetime import datetime, date
import time
import datetime
import math
from espncricinfo.summary import Summary

def diff(stime):
    a = time.strptime(stime[:19], '%Y-%m-%dT%H:%M:%S')
    t = datetime.datetime.utcnow()
    ISO_8601_time = t.isoformat()
    b = time.strptime(str(ISO_8601_time)[:19], '%Y-%m-%dT%H:%M:%S')
    a = time.mktime(a)
    b = time.mktime(b)
    d = a - b
    days = int(d) / 86400
    hours = int(d) // 3600 % 24
    minutes = math.ceil(int(d) / 60 % 60)
    seconds = int(d) % 60
    h = str(hours)
    m = str(minutes)
    if hours < 10:
        h = '0' + h
    if minutes < 10:
        m = '0' + m
    return [h, m]


def local(t):
    h = t[:2]
    m = t[3:]
    mins = int(h) * 60 + int(m) + 330
    h = mins // 60
    m = mins % 60
    m = str(m)
    return str(h) + ':' + (('0' + m) if len(m) == 1 else m)

def getCKTMatches(flag):
    conn = http.client.HTTPSConnection("hs-consumer-api.espncricinfo.com")

    payload = ""

    headers = {
        'authority': "hs-consumer-api.espncricinfo.com",
        'pragma': "no-cache",
        'cache-control': "no-cache",
        'sec-ch-ua': "^\^Chromium^^;v=^\^94^^, ^\^Google"
        }

    conn.request("GET", "/v1/pages/matches/current?lang=en&latest=true", payload, headers)

    res = conn.getresponse()
    data = res.read()

    # print(data.decode("utf-8"))
    jsonDict = json.loads(data)
    # print(jsonDict)
    ret = dict()
    i = "1"
    url_head = 'https://www.espncricinfo.com/series/'
    if flag == 0:
        for e in jsonDict['matches']:
            url = url_head + e['series']['slug'] + '-' + str(e['series']['objectId']) + '/' + e['slug'] + '-' + str(e['objectId']) + '/full-scorecard'
            # if str(date.today()) == e['startTime'][:10] and (e["teams"][0]['team']['longName'].find('India') != -1 or e["teams"][1]['team']['longName'].find('India') != -1 or e['series']['description'].find('India') != -1) :
            if str(date.today()) == e['startTime'][:10] :
                # pprint(e['teams'][1]['team']['id'])
                id1 = e['teams'][0]['team']['id']
                id2 = e['teams'][1]['team']['id']
                win = 0
                if 'winnerTeamId' in e and e['winnerTeamId'] != None:
                    idw = e['winnerTeamId']
                    if idw == id1:
                        win = 1
                    elif idw == id2:
                        win = 2
                score1 = '' if e['teams'][0]['score'] == None else (e['teams'][0]['score'] + ('' if e['teams'][0]['scoreInfo'] == None else ('(' + e['teams'][0]['scoreInfo'] + ')')))
                score2 = '' if e['teams'][1]['score'] == None else (e['teams'][1]['score'] + ('' if e['teams'][1]['scoreInfo'] == None else ('(' + e['teams'][1]['scoreInfo'] + ')')))
                # print(e['teams'][0]['score'])
                stxt = e['statusText']
                if e['statusText'] == 'Match starts in {{MATCH_START_HOURS}} {{MATCH_START_MINS}}':
                    h, m = diff(e['startTime'])
                    stxt = 'Match starts in ' + h + ' hours ' + m + ' minutes'
                ret[i] = [e['series']['longName'], e['title'] + ', ' + e['ground']['smallName'] + ' - ' + e['format'], ('Started at ' if (e['state'] == 'LIVE' or e['stage'] == 'FINISHED' or e['status'] == 'RESULT') else 'Starts at ') + local(e['startTime'][11:16]) + ', ' + ('Match ended' if e['status'] == 'RESULT' else ('-' if e['status'][0] == '{' else e['status'].upper())), e["teams"][0]['team']['longName'], e["teams"][1]['team']['longName'], score1, score2, 'Updates only' if stxt == None else stxt, e['objectId'], url, win];
                i = str(int(i) + 1)
    else:
        for e in jsonDict['matches']:
            url = url_head + e['series']['slug'] + '-' + str(e['series']['objectId']) + '/' + e['slug'] + '-' + str(e['objectId']) + '/full-scorecard'
            if e['state'] == 'LIVE' and (e["teams"][0]['team']['longName'].find('India') != -1 or e["teams"][1]['team']['longName'].find('India') != -1 or e['series']['description'].find('India') != -1):
                
                score1 = '' if e['teams'][0]['score'] == None else (e['teams'][0]['score'] + ('' if e['teams'][0]['scoreInfo'] == None else ('(' + e['teams'][0]['scoreInfo'] + ')')))
                score2 = '' if e['teams'][1]['score'] == None else (e['teams'][1]['score'] + ('' if e['teams'][1]['scoreInfo'] == None else ('(' + e['teams'][1]['scoreInfo'] + ')')))
                # print(e['teams'][0]['score'])
                stxt = e['statusText']
                if e['statusText'] == 'Match starts in {{MATCH_START_HOURS}} {{MATCH_START_MINS}}':
                    h, m = diff(e['startTime'])
                    stxt = 'Match starts in ' + h + ' hours ' + m + ' minutes'
                ret[i] = [e['series']['longName'], e['title'] + ', ' + e['ground']['smallName'] + ' - ' + e['format'], ('Started at ' if (e['state'] == 'LIVE' or e['stage'] == 'FINISHED' or e['status'] == 'RESULT') else 'Starts at ') + local(e['startTime'][11:16]) + ', ' + ('Match ended' if e['status'] == 'RESULT' else ('-' if e['status'][0] == '{' else e['status'].upper())), e["teams"][0]['team']['longName'], e["teams"][1]['team']['longName'], score1, score2, 'Updates only' if stxt == None else stxt, e['objectId'], url];
                i = str(int(i) + 1)
    return ret


# def main():
#     match_id=1254115
#     url= "https://www.espncricinfo.com/matches/engine/match/{0}.json".format(str(match_id))
#     response=requests.get(url)
#     j_son=response.json()
#     return (j_son)


pprint(getCKTMatches(0))
