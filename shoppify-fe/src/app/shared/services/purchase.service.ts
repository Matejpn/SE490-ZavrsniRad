import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class PurchaseService {
	private addPurchaseUrl = environment.baseUrl + '/purchase';
	private confirmPurchaseUrl = environment.baseUrl + '/purchase/confirm/';

	constructor(private http: HttpClient) {}

	addPurchase(purchase): Observable<any> {
		return this.http.post(this.addPurchaseUrl, purchase);
	}

	confirmPurchase(idPurchase: Number): Observable<any> {
		return this.http.put(this.confirmPurchaseUrl + idPurchase, {});
	}
}
