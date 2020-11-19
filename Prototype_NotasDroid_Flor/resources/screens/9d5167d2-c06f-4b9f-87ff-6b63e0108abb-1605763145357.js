jQuery("#simulation")
  .on("click", ".s-9d5167d2-c06f-4b9f-87ff-6b63e0108abb .click", function(event, data) {
    var jEvent, jFirer, cases;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-Button_1")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "#s-9d5167d2-c06f-4b9f-87ff-6b63e0108abb #s-Button_1": {
                      "attributes": {
                        "font-size": "0pt"
                      }
                    }
                  },{
                    "#s-9d5167d2-c06f-4b9f-87ff-6b63e0108abb #s-Button_1 > .backgroundLayer > .colorLayer": {
                      "attributes": {
                        "background-color": "#DDDDDD"
                      }
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "#s-9d5167d2-c06f-4b9f-87ff-6b63e0108abb #s-Button_1": {
                      "attributes": {
                        "font-size": "0pt"
                      }
                    }
                  },{
                    "#s-9d5167d2-c06f-4b9f-87ff-6b63e0108abb #s-Button_1 > .backgroundLayer > .colorLayer": {
                      "attributes": {
                        "background-color": "#282828"
                      }
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 100
                },
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/1e3b251b-cc38-4b26-bb5a-2f55a0d59c6b"
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-Paragraph_2")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "#s-9d5167d2-c06f-4b9f-87ff-6b63e0108abb #s-Paragraph_2": {
                      "attributes": {
                        "font-size": "0pt"
                      }
                    }
                  },{
                    "#s-9d5167d2-c06f-4b9f-87ff-6b63e0108abb #s-Paragraph_2 span": {
                      "attributes": {
                        "color": "#282828"
                      }
                    }
                  } ],
                  "exectype": "serial",
                  "delay": 0
                },
                {
                  "action": "jimChangeStyle",
                  "parameter": [ {
                    "#s-9d5167d2-c06f-4b9f-87ff-6b63e0108abb #s-Paragraph_2": {
                      "attributes": {
                        "font-size": "0pt"
                      }
                    }
                  },{
                    "#s-9d5167d2-c06f-4b9f-87ff-6b63e0108abb #s-Paragraph_2 span": {
                      "attributes": {
                        "color": "#B2B2B2"
                      }
                    }
                  } ],
                  "exectype": "timed",
                  "delay": 100
                },
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/c6a901e0-4754-4c12-bcf9-b6108d0cb37c"
                  },
                  "exectype": "serial",
                  "delay": 0
                }
              ]
            }
          ],
          "exectype": "serial",
          "delay": 0
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  });