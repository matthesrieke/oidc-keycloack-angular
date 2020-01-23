import { OAuthService, AuthConfig, JwksValidationHandler } from 'angular-oauth2-oidc';
import { EventEmitter } from '@angular/core';


export class AuthInitializer {

    public onLoginStateChanged = new EventEmitter<boolean>();
    public onTokenExpired = new EventEmitter<boolean>();

    constructor(private oauthService: OAuthService) {
    }

    public init() {
        /** subscribe to oauth events */
        const authSub = this.oauthService.events.subscribe(ev => {
            console.log('oauth event', ev);
            if (ev.type === 'token_received') {
                authSub.unsubscribe();
                // set up a expiration checker
                this.onLoginStateChanged.emit(true);
            }

        });

        this.initializeAuth();
    }

    private initializeAuth() {
        const fullDiscoveryUrl = 'http://localhost:8081/auth/realms/master/.well-known/openid-configuration';
        const clientAuthConfig: AuthConfig = {
            strictDiscoveryDocumentValidation: false,
            redirectUri: window.location.href,
            issuer: 'http://localhost:8081/auth/realms/master',
            silentRefreshRedirectUri: 'http://localhost:8081/auth/realms/master/protocol/openid-connect/login-status-iframe.html',
            clientId: 'angular-spa',
            scope: 'profile email roles eumetsat_license',
            logoutUrl: 'http://localhost:8081/auth/realms/master/protocol/openid-connect/logout',
            postLogoutRedirectUri: 'http://localhost:8081/auth/realms/master/account'
        };
        this.oauthService.configure(clientAuthConfig);
        this.oauthService.setupAutomaticSilentRefresh();
        this.oauthService.tokenValidationHandler = new JwksValidationHandler();
        this.oauthService.loadDiscoveryDocument(fullDiscoveryUrl).then(() => {
            this.oauthService.logoutUrl = clientAuthConfig.logoutUrl;
            this.oauthService.tryLogin().then(loggedIn => {
                if (!loggedIn) {
                    const token = this.oauthService.getAccessToken();
                    console.log('checking for token', token);
                    loggedIn = token ? true : false;
                }
                console.log('Login successfully?', loggedIn);
                this.onLoginStateChanged.emit(loggedIn);
            });
        });
    }


}