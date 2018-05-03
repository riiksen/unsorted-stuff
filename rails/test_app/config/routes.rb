Rails.application.routes.draw do
  resources :microposts
  resources :users
  
	root 'main#index'
	get '/signup', to: 'login#register'
	get '/login', to: 'login#login'
	get '/otp',	to: 'login#otp'
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
