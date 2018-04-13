import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { environment } from '../../environments/environment';

import 'rxjs/add/operator/map';

@Injectable()
export class SurveyDataServiceService {

  	constructor(private http: Http) { }

  	getSurveySections() {
		return this.http.get(environment.json_url)
			.map(response => response.json())
	}

	persistSurveyReponse(surveyResponse) {
		console.log("I will post this ->");
		console.log(surveyResponse);
		console.log("Here -> "+environment.save_response_url);
	}

}
