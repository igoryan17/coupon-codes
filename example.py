import json
import re

import requests

response = requests.get('https://www.papajohns.ru/api/stock/codes')
output = response.json()
moscow_regex = re.compile('.*Москва|.*все города', re.I)
price_regex = re.compile('\d+')
moscow_codes = []
with open('result.json', 'w', encoding='utf-8') as outfile:
    for code in output['codes']:
        if moscow_regex.match(code['name']):
            name = code['name']
            columns = iter(name.split(' - '))
            moscow_codes.append({'id': next(columns),
                                 'code': next(columns),
                                 'description': next(columns),
                                 'price': price_regex.findall(next(columns))[0],
                                 'locations': next(columns).split(', ')})
    json.dump(moscow_codes, outfile, ensure_ascii=False, indent=0)
