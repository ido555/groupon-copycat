import {Coupon} from '../models/coupon';
import {CouponCategory} from '../enums/coupon-category.enum';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CustomerControllerService {
  baseUrl = environment.baseUrl;
  constructor(private httpClient: HttpClient) {
  }

  public getDetails(token: string) {
    return this.httpClient.get(this.baseUrl + 'customer/details/' + token);
  }

  public getAllCoupons(token: string) {
    return this.httpClient.get(this.baseUrl + 'customer/allCoupons/' + token);
  }

  public getCouponsByCategory(token: string, type: CouponCategory) {
    return this.httpClient.get(this.baseUrl + 'customer/categoryCoupons/' + token + '/' + type);
  }

  public getCouponsUnderPrice(token: string, maxPrice: number) {
    return this.httpClient.get(this.baseUrl + 'customer/priceCoupons/' + token + '/' + maxPrice);
  }

  public purchaseCoupon(token: string, coupon: Coupon) {
    return this.httpClient.post(this.baseUrl + 'customer/buyCoupon/' + token, coupon, {responseType: 'text'});
  }
}
