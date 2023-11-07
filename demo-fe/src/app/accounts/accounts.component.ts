

import { Component, OnInit } from "@angular/core";
import { ServiceData } from "../service.data";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";


interface Accounts {
     id: number;
     address: string;
     email: string;
     isDeleted: number;
     name: string;
     password: string;
     phoneNumber: string;
     roles: string;
     username: string
}

@Component({
     selector: 'app-accounts',
     templateUrl: './accounts.component.html',
     styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

     accounts: Accounts[] = []
     account = {};
     page = 0;
     size = 10;
     totalElements = 0;
     closeResult = '';
     accountsForm:any = FormGroup;
     request = {
          page: this.page,
          size: this.size
     }
     isEdit = false;

     constructor(private serviceData:ServiceData,
          private modalService: NgbModal,
          private formBuilder:FormBuilder){}

     
     ngOnInit(): void {
          this.loadDataAccounts(this.request);
          this.initForm();
     }

     initForm(){
          this.accountsForm = this.formBuilder.group({
               id: [null, Validators.required],
               name: [null, Validators.required],
               email: [null, Validators.required],
               phoneNumber: [null, Validators.required],
               username: [null, Validators.required],
               roles: [null, Validators.required],
               password: [null, Validators.required],
               address: [null, Validators.required]
          })
     }

     loadDataAccounts(request:any){
          this.serviceData.searchAccounts(request)
               .subscribe(response => {
                    this.accounts = response.data.content;
                    this.totalElements = response.data.numberOfElements;
                    console.log(this.accounts);
               }, error => console.log(error));
     }

     open(content: any) {
          this.accountsForm.reset();
          this.accountsForm.get('roles').patchValue('');
          this.isEdit = false;
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
			},
			(reason) => {
				
			},
		);
	}

     save(){
          console.log(JSON.stringify(this.accountsForm.value));
          this.serviceData.save(this.accountsForm.value)
               .subscribe(response => {
                    if (response.status == 200){
                         console.log(response);
                         this.loadDataAccounts(this.request);
                    }
               }, error => console.log(error));
     }

     update() {
          console.log(JSON.stringify(this.accountsForm.value));
          this.serviceData.update(this.accountsForm.value)
               .subscribe(response => {
                    if (response.status == 200){
                         console.log(response);
                         this.loadDataAccounts(this.request);
                    }
               }, error => console.log(error));
     }

     detail(id:number, content:any){
          console.log("detail accouns ",id);
          this.serviceData.getAccounts(id)
               .subscribe(response => {
                    if (response.status == 200){
                         this.isEdit = true;
                         this.account = response.data;
                         console.log(this.account);
                         this.accountsForm.get('id').patchValue(response.data.id);
                         this.accountsForm.get('name').patchValue(response.data.name);
                         this.accountsForm.get('email').patchValue(response.data.email);
                         this.accountsForm.get('phoneNumber').patchValue(response.data.phoneNumber);
                         this.accountsForm.get('username').patchValue(response.data.username);
                         this.accountsForm.get('roles').patchValue(response.data.roles);
                         this.accountsForm.get('address').patchValue(response.data.address);
                         this.modalService.open(content);
                    }
               }, error => console.log(error));
     }

     nextPage(page:number){
          this.loadDataAccounts({
               page: page - 1,
               size: this.size
          });
     }
}