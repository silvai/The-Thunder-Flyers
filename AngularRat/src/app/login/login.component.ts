import { Component, OnInit, ViewEncapsulation, Input } from '@angular/core';
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

  private username: string;

  private password: string;

  private status: string;

  constructor(private us: UserService, private router: Router) { }

  ngOnInit() {
  }

  onSubmit() {
    var user = new User(this.username, this.password);
    this.us.login(user).subscribe(val => {
      if (val.success != true) {
        this.status = val.message;
      } else {
        this.router.navigate(["/dashboard"]);        
      }
    }, (err) => {
      this.status = err.toString();
    });
  }
}

