import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from 'src/app/app.constants';
import { BasicAuthenticationService } from '../basic-authentication.service';

export class HelloWorldBean {
  constructor(public message:string){ }
}

@Injectable({
  providedIn: 'root'
})
export class WelcomeDataService {

  constructor(private http:HttpClient) { }

  executeHelloWorldBeanService() {
    return this.http.get<HelloWorldBean>(`${API_URL}/hello-world-bean`);
  }

  executeHelloWorldServiceWithPathVariable(name) {
    let basicAuthHedaerString = this.createBasicAuthenticationHttpHeader();

    let headers = new HttpHeaders({ Authorization: basicAuthHedaerString })

    return this.http.get<HelloWorldBean>(`http://localhost:8080/hello-world/${name}`, 
    {headers}
    );
  }

  createBasicAuthenticationHttpHeader() {
    let username = 'bruh';
    let password = 'moment';
    let basicAuthHedaerString = 'Basic ' + window.btoa(username + ":" + password);
    return basicAuthHedaerString;
  }

}
