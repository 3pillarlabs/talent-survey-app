import { Component, Input } from "@angular/core";
import * as Survey from "survey-angular";
import * as widgets from "surveyjs-widgets";
import { SurveyDataServiceService } from "./services/survey-data-service.service";


import "inputmask/dist/inputmask/phone-codes/phone.js";

widgets.icheck(Survey);
widgets.select2(Survey);
widgets.imagepicker(Survey);
widgets.inputmask(Survey);
widgets.jquerybarrating(Survey);
widgets.jqueryuidatepicker(Survey);
widgets.nouislider(Survey);
widgets.select2tagbox(Survey);
widgets.signaturepad(Survey);
widgets.sortablejs(Survey);
widgets.ckeditor(Survey);
widgets.autocomplete(Survey);
widgets.bootstrapslider(Survey);
widgets.prettycheckbox(Survey);

Survey.JsonObject.metaData.addProperty("questionbase", "popupdescription:text");
Survey.JsonObject.metaData.addProperty("page", "popupdescription:text");


@Component({
  selector: "survey",
  providers: [SurveyDataServiceService],
  template: `<div class="survey-container contentcontainer codecontainer"><div id="surveyElement"></div></div>`
})
export class SurveyComponent {
  constructor (private surveyDataService: SurveyDataServiceService) { }
  // surveyDataService : SurveyDataServiceService;

  @Input()
  set json(value: object) {
    const surveyModel = new Survey.Model(value);
    
    surveyModel.onAfterRenderQuestion.add((survey, options) => {
      if (!options.question.popupdescription) return;

      //Add a button;
      var btn = document.createElement("button");
      btn.className = "btn btn-info btn-xs";
      btn.innerHTML = "More Info";
      var question = options.question;
      btn.onclick = function() {
        //showDescription(question);
        alert(options.question.popupdescription);
      };
      var header = options.htmlElement.querySelector("h5");
      var span = document.createElement("span");
      span.innerHTML = "  ";
      header.appendChild(span);
      header.appendChild(btn);
    });
   surveyModel.onComplete.add((result) => this.surveyComplete(result));

   Survey.StylesManager.applyTheme("default");
   Survey.SurveyNG.render("surveyElement", { model: surveyModel });
  }

  surveyComplete (result) {
    console.log("Got it!")
    console.log(JSON.stringify(result.data))
    this.surveyDataService.persistSurveyReponse(result.data)
      .subscribe(data => {console.log("Successfully posted survey to the API.")}, Error => console.log(Error))
  }

  ngOnInit() {}
}
