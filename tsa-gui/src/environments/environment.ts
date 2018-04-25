// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  // json_url: "http://www.json-generator.com/api/json/get/cdmrWwcwJe?indent=2",
  json_url: "http://localhost:8090/getQuestions",
  save_response_url: "http://localhost:8090/persistSurveyResponse"
};
