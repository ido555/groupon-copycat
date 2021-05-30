import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LoginControllerService {
  baseUrl = environment.baseUrl;
  post: object;

  constructor(private httpClient: HttpClient) {
  }

  public getAllCoupons(token: string) {
    return this.httpClient.get(this.baseUrl + 'getAllCoupons/' + token,{responseType: 'json'});
  }
  public login(clientType: string, password: string, email: string) {
    return this.httpClient.get(this.baseUrl + 'login/' + clientType + '/' + email + '/' + password, {responseType: 'text'});
  }

  public logout(token: string) {
    return this.httpClient.delete(this.baseUrl + 'logout/' + token, {responseType: 'text'});
  }
}
