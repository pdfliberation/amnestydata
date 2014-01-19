amnestydata
===========

Amnesty International Torture Data

A couple of parsers were written for the Annual Amnesty International State of Human Rights reports. The challenge, described at this link, 
https://github.com/pdfliberation/pdf-hackathon/blob/master/challenges/amnesty-challenge.md, was to extract violations of torture and build a database. The parsers were able to extract the sections on torture in varying degrees of success, but more testing needs to be done. 

A further requirement would have been to identify or produce a few fields that would have statistical value, such as frequency of abuses, type of abuses, and the agencies involved. This was attempted by one of the team members but progress was not made within the timeframe of the hackathon.

At this point, the state of the parser code is that it needs to be run for all the years requested and the results checked and debugged. At the conclusion of this step then data would be available that would consist of the "torture and other ill-treatment" sections for each country reported for all the years available (1961-2013).

These records could be easily put into a database with three simple fields, country, date, and description. This database would be be the foundation for further parsing with statistically useful extracts as the goal, assuming that is what the client is interested in.

The database would could also be used to run a RESTful API. A RESTful API would allow websites around the world to display "AI Alert" widgets on their websites that would show the abuses described. For example, a wikipedia.org page on a specific country could have a "current alert" box on the page that would show the current alert for that country. The database behind the API could be easily updated by Amnesty International staff via a simple webpage and the abuses described would then be available in real time at any website that was using the RESTful API. This would also be doubly useful in that the database would be updated in realtime and any statistical information work would remain up-to-date. 

In summary, the results of the pdfliberation hackathon is currently available at https://github.com/pdfliberation/amnestydata, but Amnesty International support staff needs to contact the contributors to determine prospects for continued work on the project.
