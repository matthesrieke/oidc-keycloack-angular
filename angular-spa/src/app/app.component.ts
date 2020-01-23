import { Component, OnInit } from '@angular/core';
import { AuthInitializer } from './auth-init';
import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'oidc-keycloack-angular';
  authInit: AuthInitializer;
  claims: any;
  claimJsonString: string;
  scopes: any;
  scopesJsonString: string;

  constructor(private oauthService: OAuthService) {

  }

  ngOnInit() {
    this.authInit = new AuthInitializer(this.oauthService);
    this.authInit.init();
    this.authInit.onLoginStateChanged.subscribe(loggedIn => {
      if (loggedIn) {
        this.claims = this.oauthService.getIdentityClaims();
        this.scopes = this.oauthService.getIdToken();
        if (this.claims) {
          this.claimJsonString = JSON.stringify(this.claims, null, 4);
          this.scopesJsonString = JSON.stringify(this.scopes);
        }
      }
    })
  }

  public login() {
    this.oauthService.redirectUri = window.location.href;
    this.oauthService.initImplicitFlow();
  }

  public logout() {
    this.oauthService.postLogoutRedirectUri = window.location.href;
    this.oauthService.redirectUri = window.location.href;
    this.oauthService.logOut();
  }


}
