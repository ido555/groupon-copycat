import {CouponCategory} from '../enums/coupon-category.enum';
import {Coupon} from '../models/coupon';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CompanyControllerService {
  baseUrl = environment.baseUrl;
  
  constructor(private httpClient: HttpClient) {
  }

  public getDetails(token: string) {
    return this.httpClient.get(this.baseUrl + 'company/details/' + token);
  }

  public getAllCoupons(token: string) {
    return this.httpClient.get(this.baseUrl + 'company/allCoupons/' + token);
  }

  public getCouponsByCategory(token: string, type: CouponCategory) {
    return this.httpClient.get(this.baseUrl + 'company/categoryCoupons/' + token + '/' + type);
  }

  public getCouponsUnderPrice(token: string, maxPrice: number) {
    return this.httpClient.get(this.baseUrl + 'company/priceCoupons/' + token + '/' + maxPrice);
  }

  public addCoupon(token: string, coupon: Coupon) {
    return this.httpClient.post(this.baseUrl + 'company/addCoupon/' + token, coupon, {responseType: 'text'});
  }

  public updateCoupon(token: string, coupon: Coupon) {
    return this.httpClient.put(this.baseUrl + 'company/updateCoupon/' + token, coupon, {responseType: 'text'});
  }

  public deleteCoupon(token: string, couponId: number) {
    return this.httpClient.delete(this.baseUrl + 'company/deleteCoupon/' + token + '/' + couponId, {responseType: 'text'});
  }
}
