import { Component } from "@angular/core";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class AppComponent {
  title = "Bi Annual Talent Engagement Survey";
  json = {
    "title": "",
    "pages": [
      {
        "name": "page1",
        "elements": [
          {
            "type": "panel",
            "name": "panel1",
            "elements": [
              {
                "type": "html",
                "name": "question1",
                "html": "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vitae erat quis eros sagittis fringilla. Etiam commodo venenatis risus, sit amet viverra mauris pellentesque vel. Quisque tempor, dui id euismod luctus, metus dui vulputate tellus, eu convallis tellus leo eget justo. Etiam non scelerisque libero. Nulla tempus id nunc in venenatis. Sed ultrices arcu ipsum, a eleifend tortor sagittis et. Duis tristique nunc sodales, posuere diam eu, dignissim dui. Suspendisse potenti. Phasellus vitae dolor lobortis, molestie lectus id, euismod sem. Morbi fringilla vitae leo quis pulvinar.</p>\n\n<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In felis neque, commodo luctus urna nec, sodales cursus odio. Phasellus a sem a sapien elementum ornare a in eros. Curabitur vehicula tempus augue ut condimentum. Duis consectetur dolor at lacinia consequat. Donec condimentum, odio in laoreet ornare, odio nisi vehicula quam, eu blandit ex mi in risus. Curabitur non risus ullamcorper, pretium enim id, finibus ligula. Duis lorem sapien, sodales in vestibulum vel, maximus et massa. Quisque sed semper lacus. Duis accumsan mi id purus pretium, at ornare quam euismod. Nullam vulputate nec tellus ac bibendum. Etiam tempor dignissim rhoncus. Morbi aliquam tempus semper.</p>\n"
              }
            ],
            "title": "Bi-Annual Talent Engagement Survery"
          }
        ]
      },
      {
        "name": "page2",
        "elements": [
          {
            "type": "panel",
            "name": "panel2",
            "elements": [
              {
                "type": "html",
                "name": "location_question",
                "html": "<p>The office location is the only selection that is required.</p>\n"
              },
              {
                "type": "radiogroup",
                "name": "location",
                "title": "Office Location",
                "isRequired": true,
                "choices": [
                  {
                    "value": "item1",
                    "text": "Noida"
                  },
                  {
                    "value": "item2",
                    "text": "Cluj"
                  },
                  {
                    "value": "item3",
                    "text": "Fairfax"
                  },
                  {
                    "value": "item4",
                    "text": "Iasi"
                  }
                ]
              },
              {
                "type": "radiogroup",
                "name": "direct_manager",
                "title": "Direct Manager?",
                "isRequired": true,
                "choices": [
                  {
                    "value": "item1",
                    "text": "Amit Bharti"
                  },
                  {
                    "value": "item2",
                    "text": "Vikash"
                  },
                  {
                    "value": "item3",
                    "text": "Adam Hann"
                  },
                  {
                    "value": "item4",
                    "text": "ABC"
                  }
                ]
              }
            ],
            "title": "Tell us little about yourself."
          }
        ]
      },
      {
        "name": "page3",
        "elements": [
          {
            "type": "rating",
            "name": "happy_at_work",
            "title": "How happy are you at work?",
            "isRequired": true,
            "rateMax": 10,
            "minRateDescription": "Not happy ",
            "maxRateDescription": " I am excited"
          },
          {
            "type": "rating",
            "name": "3pillar_culture",
            "title": "What do you feel about 3Pillars culture?",
            "isRequired": true,
            "rateMax": 10,
            "minRateDescription": "It's cold ",
            "maxRateDescription": " It's super Charging"
          },
          {
            "type": "rating",
            "name": "reffer_someone",
            "title": "How likely are you to refer someone to work at 3Pillar?",
            "isRequired": true,
            "rateMax": 10,
            "minRateDescription": "least likely",
            "maxRateDescription": " Certainly"
          },
          {
            "type": "rating",
            "name": "manager_support",
            "title": "Do you feel your manager give you enough support and guidance?",
            "isRequired": true,
            "rateMax": 10,
            "minRateDescription": "None",
            "maxRateDescription": " He is my mentor"
          }
        ]
      },
      {
        "name": "page4",
        "elements": [
          {
            "type": "panel",
            "name": "open_ended_question",
            "elements": [
              {
                "type": "comment",
                "name": "suggest_improvements",
                "title": "What is the one thing that organization could do to improve your overall happiness?",
                "rows": 5
              },
              {
                "type": "comment",
                "name": "manager_suggestions",
                "title": "What is the one thing that your manager could do to improve your overall job satisfaction?",
                "cols": 40,
                "rows": 5
              }
            ],
            "title": "Open ended Questions"
          }
        ]
      }
    ],
    "showPageTitles": false,
    "showQuestionNumbers": "off",
    "questionErrorLocation": "bottom",
    "showProgressBar": "bottom",
    "pagePrevText": "Back",
    "pageNextText": "Continue",
    "firstPageIsStarted": true
  };

  constructor() {
    
  }

  onSurveyComplete = (result) => {
    console.log(JSON.stringify(result.data))
  }

  
}

