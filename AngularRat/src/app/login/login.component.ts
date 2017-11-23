import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {User} from '../user';
import {UserService} from '../user.service';
import { Router } from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {

  model=new User("","");

  constructor(private us: UserService, private router: Router) { }

  ngOnInit() {
  }

  onSubmit() {
    this.us.login(this.model).subscribe(val => {
        if (val.status == false) {
          alert(val.message);
        } else {
          this.router.navigate(["/dashboard"]);
        }
    });
  }
}

