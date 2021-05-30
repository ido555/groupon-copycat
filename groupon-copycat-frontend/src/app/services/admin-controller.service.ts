import {Company} from '../models/company';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Customer} from '../models/customer';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AdminControllerService {
  baseUrl = environment.baseUrl;

  constructor(private httpClient: HttpClient) {
  }

  public getAllCustomers(token: string) {
    return this.httpClient.get(this.baseUrl + 'admin/getAll/customers/' + token);
  }

  public getAllCompanies(token: string) {
    return this.httpClient.get(this.baseUrl + 'admin/getAll/companies/' + token);
  }

  public getOneCustomer(token: string, customerId: number) {
    return this.httpClient.get(this.baseUrl + 'admin/get/customer/' + token + '/' + customerId);
  }

  public getOneCompany(token: string, companyId: number) {
    return this.httpClient.get(this.baseUrl + 'admin/get/company/' + token + '/' + companyId);
  }

  public addCustomer(token: string, customer: Customer) {
    return this.httpClient.post(this.baseUrl + 'admin/add/customer/' + token, customer, {responseType: 'text'});
  }

  public addCompany(token: string, company: Company) {
    return this.httpClient.post(this.baseUrl + 'admin/add/company/' + token, company, {responseType: 'text'});
  }

  public updateCustomer(token: string, customer: Customer) {
    return this.httpClient.put(this.baseUrl + 'admin/update/customer/' + token, customer, {responseType: 'text'});
  }

  public updateCompany(token: string, company: Company) {
    return this.httpClient.put(this.baseUrl + 'admin/update/company/' + token, company, {responseType: 'text'});
  }

  public deleteCustomer(token: string, customerId: number) {
    return this.httpClient.delete(this.baseUrl + 'admin/delete/customer/' + token + '/' + customerId, {responseType: 'text'});
  }

  public deleteCompany(token: string, companyId: number) {
    return this.httpClient.delete(this.baseUrl + 'admin/delete/company/' + token + '/' + companyId, {responseType: 'text'});
  }
}
