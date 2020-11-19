(function(window, undefined) {
  var dictionary = {
    "28aad21d-198b-4de0-8bbd-a267cb20e23e": "Ajustes",
    "1e3b251b-cc38-4b26-bb5a-2f55a0d59c6b": "Principal",
    "9d5167d2-c06f-4b9f-87ff-6b63e0108abb": "Login",
    "a4b551c0-ff94-4b10-bbcf-836d871d1332": "Ajustes_no",
    "c6a901e0-4754-4c12-bcf9-b6108d0cb37c": "Registro",
    "667b1ffc-11a4-42ac-b58c-6290f973eb09": "MiExpediente",
    "d12245cc-1680-458d-89dd-4f0d7fb22724": "SplashScreen",
    "5794d5d9-f287-45db-ab64-62c2830ccdd2": "MiMatricula",
    "f39803f7-df02-4169-93eb-7547fb8c961a": "Template 1",
    "bb8abf58-f55e-472d-af05-a7d1bb0cc014": "default"
  };

  var uriRE = /^(\/#)?(screens|templates|masters|scenarios)\/(.*)(\.html)?/;
  window.lookUpURL = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, url;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      url = folder + "/" + canvas;
    }
    return url;
  };

  window.lookUpName = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, canvasName;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      canvasName = dictionary[canvas];
    }
    return canvasName;
  };
})(window);