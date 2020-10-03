from django.urls import path
from django.conf.urls import url
from .views import AccountAPIView

urlpatterns = [
    path('api/login/',AccountAPIView.as_view()),

]
