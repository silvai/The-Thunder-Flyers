import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {User} from '../user';
import {UserService} from '../user.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {

  model=new User("","");

  constructor(private us: UserService) { }

  ngOnInit() {
  }
  onSubmit() {
    console.dir(this.us.login(this.model));
  }
}

