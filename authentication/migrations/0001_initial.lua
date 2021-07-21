local utils = require('migrator.utils')

return {
    up = function()
        local user_info = box.schema.create_space('user_info', {
            format = {
                { name = 'bucket_id', type = 'unsigned' },
                { name = 'uuid', type = 'string' },
                { name = 'login', type = 'string' },
                { name = 'password', type = 'string' },
            },
            if_not_exists = true,
        })

        user_info:create_index('primary', {
            parts = { 'login' },
            if_not_exists = true,
        })

        user_info:create_index('bucket_id', {
            parts = { 'bucket_id' },
            if_not_exists = true,
            unique = false
        })

        utils.register_sharding_key('user_info', {'login'})
        
        return true
    end
}