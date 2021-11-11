# from gevent import monkey as curious_george
# curious_george.patch_all(thread=False, select=False)

# from espncricinfo.summary import Summary
# from espncricinfo.match import Match 
# from espncricinfo.series import Series
# def main():
#     m = Match('1254115')
#     return m.description
# print(main())

# from pprint import pprint
# import requests
# def main():
#     match_id=1254116
#     url= "https://www.espncricinfo.com/matches/engine/match/{0}.json".format(str(match_id))
#     response=requests.get(url)
#     j_son=response.json()
#     return (j_son)
# pprint(main())


from bs4 import BeautifulSoup as soup
from urllib.request import urlopen
from pprint import pprint
import bs4
import requests
import lxml
import io
import unidecode

def livecard(url):
    html_text = requests.get(url).text
    # print(html_text)
    #Open that site
    # op = urlopen(site) 

    # # read data from site
    # rd = op.read() 
    # # # close the object
    # op.close()   
    # scrapping data from site
    ret = dict()
    totCount = 0
    batCount = 0
    bowlCount = 0
    ret['headers'] = []
    sou = soup(html_text,'lxml') 
    scorecard = sou.find_all('table', class_ = 'table table-left mb-0') 

    i = 0
    # pom = soup.find('div', class_ = 'playerofthematch-player-detail')
    # s = soup(pom, 'lxml')
    # print(pom.find('span', class_ = 'playerofthematch-name').text + pom.find('span', class_ = 'playerofthematch-teamName').text)
    # pom_score = soup.find('div', class_ = 'playerofthematch-score-text')
    # print(pom_score.text)

    # print(scorecard)
    
    # for score in scorecard:  
    #     for x in score:
    #         # print(x.text[0])
    #         i = '1'
    #         for y in x:
    #             # print()
    #             if len(y) >= 7 and y.text[:7] == 'BATTING':
    #                 batCount += 1
    #                 flag = 1
    #                 continue
    #             # if y[:7] == 'Extras(':
    #             #     if extrasCount == 0:
    #             #         ret['extras1'] = y
    #             for z in y:
    #                 if flag == 1:
    #                     # ret += (z.text) + '\n'
    #                     ret['team1batting'][i].append(z.text)

    #                     # print(z.text)
    #                     print('-'*110)
    #             # ret += (y.text + '\n')
    #             # print(y.text)
    #     # ret += '\n'

    #     # print('-'*110)

    ret = {};
    ret['bat1'] = [];
    ret['bat2'] = [];
    ret['bowl1'] = [];
    ret['bowl2'] = [];
    
    i = 0
    for score in scorecard:  
        for x in score:
            # for y in x:
                # for z in y:
            if i == 1:
                j = '1'
                for y in x:
                    for z in y:
                        ret['bat' + j].append(unidecode.unidecode(z.text))
                        # print(y.text)
                    j = str(int(j) + 1)
                    # print('-'*110)
                # pprint('Rama')
            if i == 3:
                j = '1'
                for y in x:
                    if j == '3':
                        break
                    for z in y:
                        ret['bowl' + j].append(unidecode.unidecode(z.text))
                        # print(y.text)
                    j = str(int(j) + 1)
                    # print('-'*110)
            i += 1
    # l = []
    return (ret)
    # ret['team1batting'] = {}
    # ret['team2batting'] = {}
    # ret['team1bowling'] = {}
    # ret['team2bowling'] = {}
    # for score in scorecard:  
    #     flag = 0
    #     for x in score:
    #         # for y in x:
    #         #     for z in y:
    #         # print(x.text)
    #         if x.text[:5] == 'TOTAL':
    #             totCount += 1
    #             if totCount == 1:
    #                 j = '1'
    #                 for y in x:
    #                     # print(y.text)
    #                     ret['t1' + j] = unidecode.unidecode(y.text)
    #                     j = str(int(j) + 1)
    #             elif totCount == 2:
    #                 j = '1'
    #                 for y in x:
    #                     # print(y.text)
    #                     ret['t2' + j] = unidecode.unidecode(y.text)
    #                     j = str(int(j) + 1)

    #         elif x.text[:7] == 'BATTING':
    #             batCount += 1
    #             flag = 1
    #             continue
    #         elif flag == 1 and batCount == 1:
    #             i = '1'
    #             for y in x:
    #                 if y.text == '':
    #                     continue
    #                 ret['team1batting'][i] = []
    #                 for z in y:
    #                     # print(z.text)
    #                     ret['team1batting'][i].append(unidecode.unidecode(z.text))
    #                 i = str(int(i) + 1)
    #         elif flag == 1 and batCount == 2:
    #             i = '1'
    #             for y in x:
    #                 if y.text == '':
    #                     continue
    #                 ret['team2batting'][i] = []
    #                 for z in y:
    #                     # print(z.text)
    #                     ret['team2batting'][i].append(unidecode.unidecode(z.text))
    #                 i = str(int(i) + 1)

    #         # elif x.text[:7] == 'BOWLING':
    #         #     bowlCount += 1
    #         #     flag = 2
    #         #     continue
    #         # elif flag == 2 and bowlCount == 1:
    #         #     i = '1'
    #         #     for y in x:
    #         #         if y.text == '':
    #         #             continue
    #         #         ret['team1bowling'][i] = []
    #         #         for z in y:
    #         #             # print(z.text)
    #         #             ret['team1bowling'][i].append(unidecode.unidecode(z.text))
    #         #         i = str(int(i) + 1)
    #         # elif flag == 2 and bowlCount == 2:
    #         #     i = '1'
    #         #     for y in x:
    #         #         if y.text == '':
    #         #             continue
    #         #         ret['team2bowling'][i] = []
    #         #         for z in y:
    #         #             # print(z.text)
    #         #             ret['team2bowling'][i].append(unidecode.unidecode(z.text))
    #         #         i = str(int(i) + 1)



    # for score in wiccard:  
    #     flag = 0
    #     for x in score:
    #         # for y in x:
    #         #     for z in y:
    #         # print(x.text)
    #         # if x.text[:5] == 'TOTAL':
    #         #     totCount += 1
    #         #     if totCount == 1:
    #         #         j = '1'
    #         #         for y in x:
    #         #             # print(y.text)
    #         #             ret['t1' + j] = unidecode.unidecode(y.text)
    #         #             j = str(int(j) + 1)
    #         #     elif totCount == 2:
    #         #         j = '1'
    #         #         for y in x:
    #         #             # print(y.text)
    #         #             ret['t2' + j] = unidecode.unidecode(y.text)
    #         #             j = str(int(j) + 1)

    #         # elif x.text[:7] == 'BATTING':
    #         #     batCount += 1
    #         #     flag = 1
    #         #     continue
    #         # elif flag == 1 and batCount == 1:
    #         #     i = '1'
    #         #     for y in x:
    #         #         if y.text == '':
    #         #             continue
    #         #         ret['team1batting'][i] = []
    #         #         for z in y:
    #         #             # print(z.text)
    #         #             ret['team1batting'][i].append(unidecode.unidecode(z.text))
    #         #         i = str(int(i) + 1)
    #         # elif flag == 1 and batCount == 2:
    #         #     i = '1'
    #         #     for y in x:
    #         #         if y.text == '':
    #         #             continue
    #         #         ret['team2batting'][i] = []
    #         #         for z in y:
    #         #             # print(z.text)
    #         #             ret['team2batting'][i].append(unidecode.unidecode(z.text))
    #         #         i = str(int(i) + 1)

    #         if x.text[:7] == 'BOWLING':
    #             bowlCount += 1
    #             flag = 2
    #             continue
    #         elif flag == 2 and bowlCount == 1:
    #             i = '1'
    #             for y in x:
    #                 if y.text == '':
    #                     continue
    #                 ret['team1bowling'][i] = []
    #                 for z in y:
    #                     # print(z.text)
    #                     ret['team1bowling'][i].append(unidecode.unidecode(z.text))
    #                 i = str(int(i) + 1)
    #         elif flag == 2 and bowlCount == 2:
    #             i = '1'
    #             for y in x:
    #                 if y.text == '':
    #                     continue
    #                 ret['team2bowling'][i] = []
    #                 for z in y:
    #                     # print(z.text)
    #                     ret['team2bowling'][i].append(unidecode.unidecode(z.text))
    #                 i = str(int(i) + 1)


    # #         print('-'*110)
    #     # fall = soup.find_all('div', class_ = 'cb-col cb-col-100 cb-ltst-wgt-hdr') 
    # return ret


def scorecard(url):
    html_text = requests.get(url).text
    # print(html_text)
    #Open that site
    # op = urlopen(site) 

    # # read data from site
    # rd = op.read() 
    # # # close the object
    # op.close()   
    # scrapping data from site
    ret = dict()
    totCount = 0
    batCount = 0
    bowlCount = 0
    ret['headers'] = []
    sou = soup(html_text,'lxml') 
    scorecard = sou.find_all('table', class_ = 'table batsman') 
    headers = sou.find_all('h5', class_ = 'header-title label') 

    wiccard = sou.find_all('table', class_ = 'table bowler') 

    i = 0
    for h in headers:
        if i < 2:
            ret['headers'].append(h.text)
        i += 1
    if ret['headers'][1].find('INNINGS') == -1:
        ret['headers'].pop()
    # pom = soup.find('div', class_ = 'playerofthematch-player-detail')
    # s = soup(pom, 'lxml')
    # print(pom.find('span', class_ = 'playerofthematch-name').text + pom.find('span', class_ = 'playerofthematch-teamName').text)
    # pom_score = soup.find('div', class_ = 'playerofthematch-score-text')
    # print(pom_score.text)

    # print(scorecard)
    
    # for score in scorecard:  
    #     for x in score:
    #         # print(x.text[0])
    #         i = '1'
    #         for y in x:
    #             # print()
    #             if len(y) >= 7 and y.text[:7] == 'BATTING':
    #                 batCount += 1
    #                 flag = 1
    #                 continue
    #             # if y[:7] == 'Extras(':
    #             #     if extrasCount == 0:
    #             #         ret['extras1'] = y
    #             for z in y:
    #                 if flag == 1:
    #                     # ret += (z.text) + '\n'
    #                     ret['team1batting'][i].append(z.text)

    #                     # print(z.text)
    #                     print('-'*110)
    #             # ret += (y.text + '\n')
    #             # print(y.text)
    #     # ret += '\n'

    #     # print('-'*110)

    # for score in wiccard:  
    #     for x in score:
    #         for y in x:
    #             for z in y:
    #                 print(z.text)
    #                 print('-'*110)
    l = []

    ret['team1batting'] = {}
    ret['team2batting'] = {}
    ret['team1bowling'] = {}
    ret['team2bowling'] = {}
    for score in scorecard:  
        flag = 0
        for x in score:
            # for y in x:
            #     for z in y:
            # print(x.text)
            if x.text[:5] == 'TOTAL':
                totCount += 1
                if totCount == 1:
                    j = '1'
                    for y in x:
                        # print(y.text)
                        ret['t1' + j] = unidecode.unidecode(y.text)
                        j = str(int(j) + 1)
                elif totCount == 2:
                    j = '1'
                    for y in x:
                        # print(y.text)
                        ret['t2' + j] = unidecode.unidecode(y.text)
                        j = str(int(j) + 1)

            elif x.text[:7] == 'BATTING':
                batCount += 1
                flag = 1
                continue
            elif flag == 1 and batCount == 1:
                i = '1'
                for y in x:
                    if y.text == '':
                        continue
                    ret['team1batting'][i] = []
                    for z in y:
                        # print(z.text)
                        ret['team1batting'][i].append(unidecode.unidecode(z.text))
                    i = str(int(i) + 1)
            elif flag == 1 and batCount == 2:
                i = '1'
                for y in x:
                    if y.text == '':
                        continue
                    ret['team2batting'][i] = []
                    for z in y:
                        # print(z.text)
                        ret['team2batting'][i].append(unidecode.unidecode(z.text))
                    i = str(int(i) + 1)

            # elif x.text[:7] == 'BOWLING':
            #     bowlCount += 1
            #     flag = 2
            #     continue
            # elif flag == 2 and bowlCount == 1:
            #     i = '1'
            #     for y in x:
            #         if y.text == '':
            #             continue
            #         ret['team1bowling'][i] = []
            #         for z in y:
            #             # print(z.text)
            #             ret['team1bowling'][i].append(unidecode.unidecode(z.text))
            #         i = str(int(i) + 1)
            # elif flag == 2 and bowlCount == 2:
            #     i = '1'
            #     for y in x:
            #         if y.text == '':
            #             continue
            #         ret['team2bowling'][i] = []
            #         for z in y:
            #             # print(z.text)
            #             ret['team2bowling'][i].append(unidecode.unidecode(z.text))
            #         i = str(int(i) + 1)



    for score in wiccard:  
        flag = 0
        for x in score:
            # for y in x:
            #     for z in y:
            # print(x.text)
            # if x.text[:5] == 'TOTAL':
            #     totCount += 1
            #     if totCount == 1:
            #         j = '1'
            #         for y in x:
            #             # print(y.text)
            #             ret['t1' + j] = unidecode.unidecode(y.text)
            #             j = str(int(j) + 1)
            #     elif totCount == 2:
            #         j = '1'
            #         for y in x:
            #             # print(y.text)
            #             ret['t2' + j] = unidecode.unidecode(y.text)
            #             j = str(int(j) + 1)

            # elif x.text[:7] == 'BATTING':
            #     batCount += 1
            #     flag = 1
            #     continue
            # elif flag == 1 and batCount == 1:
            #     i = '1'
            #     for y in x:
            #         if y.text == '':
            #             continue
            #         ret['team1batting'][i] = []
            #         for z in y:
            #             # print(z.text)
            #             ret['team1batting'][i].append(unidecode.unidecode(z.text))
            #         i = str(int(i) + 1)
            # elif flag == 1 and batCount == 2:
            #     i = '1'
            #     for y in x:
            #         if y.text == '':
            #             continue
            #         ret['team2batting'][i] = []
            #         for z in y:
            #             # print(z.text)
            #             ret['team2batting'][i].append(unidecode.unidecode(z.text))
            #         i = str(int(i) + 1)

            if x.text[:7] == 'BOWLING':
                bowlCount += 1
                flag = 2
                continue
            elif flag == 2 and bowlCount == 1:
                i = '1'
                for y in x:
                    if y.text == '':
                        continue
                    ret['team1bowling'][i] = []
                    for z in y:
                        # print(z.text)
                        ret['team1bowling'][i].append(unidecode.unidecode(z.text))
                    i = str(int(i) + 1)
            elif flag == 2 and bowlCount == 2:
                i = '1'
                for y in x:
                    if y.text == '':
                        continue
                    ret['team2bowling'][i] = []
                    for z in y:
                        # print(z.text)
                        ret['team2bowling'][i].append(unidecode.unidecode(z.text))
                    i = str(int(i) + 1)


    #         print('-'*110)
        # fall = soup.find_all('div', class_ = 'cb-col cb-col-100 cb-ltst-wgt-hdr') 
    # pprint(ret)
    return ret
pprint(livecard('https://www.espncricinfo.com/series/major-clubs-limited-over-tournament-2021-22-1284321/sinhalese-sports-club-vs-saracens-sports-club-group-b-1284357/live-cricket-score'))

# pprint(scorecard('https://www.espncricinfo.com/series/pakistan-shaheens-in-sri-lanka-2021-22-1283198/sri-lanka-a-vs-pakistan-shaheens-1st-unofficial-test-1283201/full-scorecard'))