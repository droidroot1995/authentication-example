local cartridge = require('cartridge')
local vshard = require('vshard')
local cartridge_pool = require('cartridge.pool')
local cartridge_rpc = require('cartridge.rpc')
local log = require('log')

function get_schema()
    for _, instance_uri in pairs(cartridge_rpc.get_candidates('app.roles.storage', { leader_only = true })) do
        local conn = cartridge_pool.connect(instance_uri)
        return conn:call('ddl.get_schema', {})
    end
end

local USER_BUCKET_ID_FIELD = 1
local USER_UUID_FIELD = 2
local USER_LOGIN_FIELD = 3
local USER_PASSWORD_FIELD = 4
local LOGIN_UUID_UUID_FIELD = 2

function get_user_by_login(login)

    local bucket_id = vshard.router.bucket_id_mpcrc32(login)

    local user = vshard.router.callbro(bucket_id, 'box.space.user_info:get', {login})

    if user == nil then
        return nil
    end

    return user
end

function create_user(uuid, login, password_hash)
    local bucket_id = vshard.router.bucket_id_mpcrc32(login)

    local _, err = vshard.router.callrw(bucket_id, 'box.space.user_info:insert', {
        {bucket_id, uuid, login, password_hash }
    })

    if err ~= nil then
        log.error(err)
        return nil
    end

    return login
end

function delete_user_by_login(login)

    local user = get_user_by_login(login)

    if user ~= nil then

        local bucket_id = vshard.router.bucket_id_mpcrc32(user[USER_LOGIN_FIELD])

        local _, _ = vshard.router.callrw(bucket_id, 'box.space.user_info:delete', {
            {user[USER_LOGIN_FIELD]}
        })

        return user
    end

    return nil

end

function update_user_by_login(login, new_password_hash)
    local user = get_user_by_login(login)

    if user ~= nil then
        local bucket_id = vshard.router.bucket_id_mpcrc32(user[USER_LOGIN_FIELD])

        local user, err = vshard.router.callrw(bucket_id, 'box.space.user_info:update', { user[USER_LOGIN_FIELD], {
            {'=', USER_PASSWORD_FIELD, new_password_hash }}
        })

        if err ~= nil then
            log.error(err)
            return nil
        end

        return user
    end

    return nil
end

local function init(opts) -- luacheck: no unused args
    rawset(_G, 'ddl', { get_schema = get_schema })
    return true
end

local function stop()
    return true
end

local function validate_config(conf_new, conf_old) -- luacheck: no unused args
    return true
end

local function apply_config(conf, opts) -- luacheck: no unused args
    -- if opts.is_master then
    -- end

    return true
end

return {
    role_name = 'router',
    init = init,
    stop = stop,
    validate_config = validate_config,
    apply_config = apply_config,
    dependencies = {'cartridge.roles.vshard-router'},
}