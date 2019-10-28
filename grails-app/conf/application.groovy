grails.plugin.springsecurity.rest.token.storage.jwt.secret = "r4ul3o-Dt9k9ddveMgGNf2VT0EBq2yA4vc0dHCBKavqI9psGQI4ilObc8zx1WZZqFsEbFRwyTeeH4AhG7fkcHupbjsqmIMgL9Yn5t5mVTiZElCwckljmLs8g9BDSj0GYLboQ6V3aBfV4LVvVh2zsxN10mi91DDhlN0eT732Kmx8lGFbnaXDRquMiQSu6ZB4w4Xx58JVUa8Pds5cfXBP7H-bf4c5aIf48cyxMaYwsDcvujI05YmlQq_gP8WyFwaa5sHKJbciQxawMs0xNIGCQH-qTbJ0hSCN-57VDUgMWR-CMA1EMcIVIst_6O6j9CHTf3k3WoDYfDKfymOKWT-Hb4Q"
grails.plugin.springsecurity.rest.token.storage.jwt.expiration = 36000
grails.plugin.springsecurity.rest.token.storage.jwt.useSignedJwt = true

grails.plugin.springsecurity.userLookup.userDomainClassName = 'mylotte_backend.Usuario'
grails.plugin.springsecurity.userLookup.usernamePropertyName = 'email'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'mylotte_backend.UsuarioAutoridade'
grails.plugin.springsecurity.authority.className = 'mylotte_backend.Autoridade'
grails.plugin.springsecurity.requestMap.className = 'mylotte_backend.RequestMap'
grails.plugin.springsecurity.securityConfigType = 'Requestmap'
grails.plugin.springsecurity.rest.token.validation.enableAnonymousAccess = true
grails.plugin.springsecurity.filterChain.chainMap = [
    // start open URLS: these urls are inside api but are open to not authenticated users
    [pattern: '/api/login/', filters:'none'],
    [pattern: '/login/auth', filters:'anonymousAuthenticationFilter'],
    [pattern: '/oauth/access_token', filters:'anonymousAuthenticationFilter,securityRequestHolderFilter,securityContextPersistenceFilter,securityContextHolderAwareRequestFilter'],
    [pattern: '/oauth/authenticate/google', filters:'anonymousAuthenticationFilter,securityRequestHolderFilter,securityContextPersistenceFilter,securityContextHolderAwareRequestFilter'],
    [pattern: '/oauth/callback/google', filters:'anonymousAuthenticationFilter,securityRequestHolderFilter,securityContextPersistenceFilter,securityContextHolderAwareRequestFilter'],
    // end open URLS

    // start main urls
    [pattern: '/api/**', filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
    [pattern: '/**', filters:'none']
    // End main URLS
]

grails.plugin.springsecurity.rest.token.validation.active = true
grails.plugin.springsecurity.rest.token.validation.useBearerToken = false
grails.plugin.springsecurity.rest.token.validation.headerName = 'Authorization'
grails.plugin.springsecurity.rest.token.rendering.tokenPropertyName = "access_token"
grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'mylotte_backend.AuthenticationToken'

