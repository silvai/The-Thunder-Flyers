import { Component, OnInit, ViewEncapsulation, Input } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { UserMode } from '../usermode';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {
  usermodes: string[] = ["User", "Admin"];

  private firstname: string;

  private lastname: string;

  private username: string;

  private password: string;

  private usermode: string = "User";

  private status: string;
  
  constructor(private us: UserService, private router: Router) { }

  ngOnInit() {
  }

  onSubmit() {
    var type: UserMode;
    if (this.usermode === "User") {
      type = UserMode.USER;
    } else if (this.usermode === "Admin") {
      type = UserMode.ADMIN;
    }

    var user: User = new User(this.username, this.password, this.firstname, this.lastname, type);

    this.us.register(user).subscribe(val => {
      if (val.success != true) {
        this.status = val.message;
      } else {
        this.router.navigate(["/login"]);
      }
    });

  }
}
