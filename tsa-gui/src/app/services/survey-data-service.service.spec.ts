import { TestBed, inject } from '@angular/core/testing';

import { SurveyDataServiceService } from './survey-data-service.service';

describe('SurveyDataServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SurveyDataServiceService]
    });
  });

  it('should be created', inject([SurveyDataServiceService], (service: SurveyDataServiceService) => {
    expect(service).toBeTruthy();
  }));
});
