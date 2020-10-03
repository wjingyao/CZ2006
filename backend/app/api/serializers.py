from rest_framework import serializers
from users.models import User


## User
class AccountSerializer(serializers.ModelSerializer):
    class Meta :
        model = User
        fields = ('id','username','name','email')
