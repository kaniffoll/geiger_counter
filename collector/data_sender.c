#include "collector.h"
#include <yyjson.h>

#define JSON_KEY "geiger_value"
#define CONTENT_TYPE_HEADER "Content-Type: application/json"
#define ACCEPT_HEADER "Accept: application/json"
#define URL "https://geiger-server:8443"

short send_as_json(const char *s) {
    CURL *curl = curl_easy_init();

    if (!curl) {
        curl_easy_cleanup(curl);
        return -1;
    }

    const char *create_json(const char *s, yyjson_mut_doc *doc);
    yyjson_mut_doc *doc = yyjson_mut_doc_new(NULL);

    const char *json = create_json(s, doc);
    struct curl_slist *headers = NULL;
    headers = curl_slist_append(headers, CONTENT_TYPE_HEADER);
    headers = curl_slist_append(headers, ACCEPT_HEADER);

    curl_easy_setopt(curl, CURLOPT_URL, URL);
    curl_easy_setopt(curl, CURLOPT_CAINFO, "ca.crt");
    curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);
    curl_easy_setopt(curl, CURLOPT_CUSTOMREQUEST, "PUT");
    curl_easy_setopt(curl, CURLOPT_POSTFIELDS, json);
    curl_easy_setopt(curl, CURLOPT_POSTFIELDSIZE, strlen(json));
    curl_easy_setopt(curl, CURLOPT_TIMEOUT, 5L);

    CURLcode rc = curl_easy_perform(curl);

    if (rc != CURLE_OK)
        fprintf(stderr, "Error, can't perform curl request on URL: %s\n", URL);

    yyjson_mut_doc_free(doc);
    curl_easy_cleanup(curl);

    return 0;
}

const char *create_json(const char *s, yyjson_mut_doc *doc) {
    yyjson_mut_val *root = yyjson_mut_obj(doc);

    yyjson_mut_val *key = yyjson_mut_str(doc, JSON_KEY);
    yyjson_mut_val *value = yyjson_mut_str(doc, s);
    yyjson_mut_obj_add(root, key, value);

    yyjson_mut_doc_set_root(doc, root);
    return yyjson_mut_write(doc, 0, NULL);
}
