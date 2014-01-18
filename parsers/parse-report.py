#!/usr/bin/python
#coding=UTF-8

import sys
import re

lines = open('/Users/paul/Dropbox/Amnesty Reports/2009.txt').read().decode('utf8', 'ignore').split("\n")

foundCountryEntries = False
lastIndex = -1

for idx, val in enumerate(lines):
    # 2012, 2013 = caps
    # 2011 = mixed case
    if val.find("COUNTRY ENTRIES") != -1 or val.find("couNtry eNtrIes") != -1:
        foundCountryEntries = True

    if val.find("PART THREE") != -1:
        break

    if foundCountryEntries:
        val = val.strip().replace(u"\ufffc", "")
        if re.search("^\d+$", val):
            continue
        if re.search("\d", val):
            continue
        if len(val) < 5:
            continue

        # 2011 has lines with ef bf bc (ufffc) followed by a single uppercase letter
        if re.search(u"[\ufffc]+[A-Z]$", val):
            continue

        if val.upper() == val and lines[idx+1] != lines[idx+1].upper():
            print val.encode("UTF-8").strip()
            lastIndex = idx

        if val.find("Torture and other ill-treatment") != -1:
            torture_idx = idx
            while 1:
                if len(lines[torture_idx]) < 50 and len(lines[torture_idx]) and lines[torture_idx][0] == lines[torture_idx][0].upper():
                    break
                print lines[torture_idx].strip().replace(u"\ufffc", "").encode("UTF-8")
                torture_idx = torture_idx + 1

