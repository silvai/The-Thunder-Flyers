import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { UserService } from '../user.service';
import { UserMode } from '../usermode';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AdminComponent implements OnInit {

  private isAdmin: boolean;
  private loading: boolean = true;

  constructor(private us: UserService) { }

  ngOnInit() {
    var userId: number = parseInt(localStorage.getItem("userid"));
    this.us.getUser(userId).subscribe((user) => {
      console.dir(user);
      if (user.userType.valueOf() === "ADMIN") {
        this.isAdmin = true;
      }
    });
    this.loading = false;
  }

  unban() {
    this.us.adminUnban().subscribe((apimessage) => {
      alert(apimessage.message);
    });
  }
}
