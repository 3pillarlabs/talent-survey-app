import { Component } from "@angular/core";
import { SurveyDataServiceService } from "./services/survey-data-service.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class AppComponent {
  surveyData : any;

  title = "Talent Engagement Survey";
  json = this.surveyData;

  constructor(surveryDataService: SurveyDataServiceService) {
    surveryDataService.getSurveySections()
      .subscribe(
         surveyData => this.json = this.parseSurveyData(surveyData),
         error => console.log("Error: " + error),
         () => console.log("Fetched Survey Data")
       )
  }

  parseSurveyData = (surveyData) => {
    let typeMapping = {
      text: {type: 'comment', keys: [{expectedKey: 'title', apiKey: 'element'}]},
      radiogroup: {type: 'radiogroup', keys: [
        {expectedKey: 'choices', apiKey: 'options'}, 
        {expectedKey: 'title', apiKey: 'element'}
      ]},
      bullet: {type: 'radiogroup', keys: [
        {expectedKey: 'choices', apiKey: 'options'}, 
        {expectedKey: 'title', apiKey: 'element'}
      ]},
      html: {type: 'html', keys: [{expectedKey: 'html', apiKey: 'element'}]},
      rating: {type: 'rating', keys: [
        {expectedKey: 'rateMin', apiKey: 'minValue'}, 
        {expectedKey: 'rateMax', apiKey: 'maxValue'},
        {expectedKey: 'title', apiKey: 'element'}
      ]}
    }

    let keysBefore : Array<string> = Object.keys(surveyData);
    surveyData.title = "";
    surveyData.showPageTitles = false;
    surveyData.showQuestionNumbers = "off";
    surveyData.questionErrorLocation = "bottom";
    surveyData.showProgressBar = "bottom";
    surveyData.pagePrevText = "Back";
    surveyData.pageNextText = "Continue";
    surveyData.firstPageIsStarted = true;
    surveyData.pages = [];
    
    let panelCounter = 1;
    for (var k of keysBefore) {
      var pageObj = {}
      pageObj['name'] = 'page' + parseInt(k);
      pageObj['elements'] = [];

      var elementObj = {}
      elementObj['type'] = 'panel';
      elementObj['name'] = 'panel' + (panelCounter);
      panelCounter++;
      elementObj['elements'] = [];

      for (var item of surveyData[k]) {
        var itemObj = {}
        let itemType = item['type'];
        itemType = itemType.toLowerCase();
        let typeMappingObj = typeMapping[itemType];
        itemObj['type'] = typeMappingObj.type;
        for (let o of typeMappingObj.keys) {
          itemObj[o.expectedKey] = item[o.apiKey]  
        }
        itemObj['name'] = "" + item.elementId;
        // console.log(itemObj);
        elementObj['elements'].push(itemObj);
        elementObj['title'] = item['title'];
      }

      pageObj['elements'].push(elementObj);
      surveyData['pages'].push(pageObj);
    }

    surveyData.pages.sort((a,b) => {
      if (a.name < b.name) {
        return -1;
      } 
      if (a.name > b.name) {
        return 1;
      }
      return 0;
    })

    console.log(surveyData);
    return surveyData;
  }

  onSurveyComplete = (result) => {
    // console.log("post to ")
    console.log(JSON.stringify(result.data))
  }
}

