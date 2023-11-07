import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from "rxjs";

@Injectable({
     providedIn: 'root'
})
export class ServiceData {
     
     HTTP_LOCAL_URL = "http://localhost:8181/api/v1/accounts";

     constructor(public httpClient:HttpClient,){}

     searchAccounts(data:any):Observable<any>{
          const params = data;
          return this.httpClient.get(this.HTTP_LOCAL_URL+"/search", {params});
     }

     save(data:any):Observable<any>{
          return this.httpClient.post(this.HTTP_LOCAL_URL+"/save", data);
     }

     update(data:any):Observable<any> {
         return this.httpClient.put(this.HTTP_LOCAL_URL+"/update", data); 
     }

     getAccounts(id:number):Observable<any> {
          return this.httpClient.get(this.HTTP_LOCAL_URL+"/"+id);
     }

     deleteAccounts(id:number):Observable<any> {
          return this.httpClient.delete(this.HTTP_LOCAL_URL+"/"+id+"/delete");
     }
}